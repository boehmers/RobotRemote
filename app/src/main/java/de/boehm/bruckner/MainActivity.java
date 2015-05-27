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
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;


public class MainActivity extends ActionBarActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter bAdapter;
    private BluetoothSocket robot;
    private final UUID appUUID = UUID.randomUUID();
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice robotDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                try {
                    robot = robotDevice.createRfcommSocketToServiceRecord(appUUID);
                } catch (IOException e) {
                    Log.e("Connection Failure!", "Bluetooth-Connection failed.", e);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpBluetooth();
        Button messageButton = (Button) findViewById(R.id.messageButton);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    robot.connect();
                    OutputStream outputStream = robot.getOutputStream();
                    // TODO: Write the command buffer!
                } catch (IOException e) {
                    Log.e("Connection Failure!", "Bluetooth-Connection failed.", e);
                }
            }
        });
        setContentView(R.layout.activity_main);
    }

    private void setUpBluetooth() {
        // 1. Get the adapter
        bAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bAdapter == null) {
            Toast.makeText(getBaseContext(), "Bluetooth not supported on this device!", Toast.LENGTH_SHORT);
        }
        // 2. BroadcastReceiver for found devices
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
        // 3. Enable Bluetooth
        if(!bAdapter.isEnabled()) {
            Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            // TODO
            bAdapter.startDiscovery();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
