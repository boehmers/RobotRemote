package de.boehm.bruckner;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class MainActivity extends ActionBarActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private static final int ROBOT_CLASS_KEY = 2052;
    private BluetoothAdapter bAdapter;
    private BluetoothSocket robot;
    private BluetoothDevice robotDevice;
    private final UUID appUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpBluetooth();
        Button messageButton = (Button) findViewById(R.id.messageButton);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OutputStream outputStream = null;
                try {
                    outputStream = robot.getOutputStream();
                    // TODO: Write the command buffer!
                    OutputStreamWriter writer = new OutputStreamWriter(outputStream);
                    byte msg = 1;
                    writer.write(msg);
                    writer.flush();
                    outputStream.flush();
                } catch (IOException e) {
                    Log.e("Connection Failure!", "Bluetooth-Connection failed.", e);
                }
            }
        });
    }

    private void setUpBluetooth() {
        // 1. Get the adapter
        bAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bAdapter == null) {
            Toast.makeText(getBaseContext(), "Bluetooth not supported on this device!", Toast.LENGTH_SHORT);
        }

        Set<BluetoothDevice> pairedDevices = bAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            List<String> devices = new ArrayList<>();
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                if (device.getBluetoothClass().getDeviceClass() == ROBOT_CLASS_KEY) {
                    devices.add(device.getName());
                    devices.add(device.getAddress());
                    robotDevice = device;
                }
            }
            TextView robotName = (TextView) findViewById(R.id.robotName);
            robotName.setText(devices.get(0));
            TextView robotMac = (TextView) findViewById(R.id.robotMac);
            robotMac.setText(devices.get(1));
            BluetoothDevice actualRobotDevice = bAdapter.getRemoteDevice(robotDevice.getAddress());
            try {
                robot = actualRobotDevice.createRfcommSocketToServiceRecord(appUUID);
                robot.connect();
            } catch (IOException e) {
                Log.e("ERROR: ", "Connection Failure: ", e);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reconnect) {
            // TODO reconnect!
            setUpBluetooth();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
