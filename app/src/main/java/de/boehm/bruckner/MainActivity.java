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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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

    private MoveController moveController;
    private GrabController grabController;
    private CommunicationController communicationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpBluetooth();
        OutputStream outputStream = null;
        try {
            outputStream = robot.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            moveController = new MoveControllerImpl(writer);
            grabController = new GrabControllerImpl(writer);
            communicationController = new CommunicationControllerImpl(writer);
        } catch (IOException e) {
            Log.e("Connection Failure!", "Bluetooth-Connection failed.", e);
        }
        final ToggleButton forwardButton = (ToggleButton) findViewById(R.id.forwardButton);
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (forwardButton.isChecked()) {
                    moveController.startMovingForward();
                } else {
                    moveController.stopMovingForward();
                }
            }
        });
        final ToggleButton backwardButton = (ToggleButton) findViewById(R.id.backwardButton);
        backwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(backwardButton.isChecked()) {
                    moveController.startMovingBackward();
                } else {
                    moveController.stopMovingBackward();
                }
            }
        });
        final ToggleButton leftButton = (ToggleButton) findViewById(R.id.leftButton);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(leftButton.isChecked()) {
                    moveController.startTurnLeft();
                } else {
                    moveController.stopTurnLeft();
                }
            }
        });
        final ToggleButton rightButton = (ToggleButton) findViewById(R.id.rightButton);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rightButton.isChecked()) {
                    moveController.startTurnRight();
                } else {
                    moveController.stopTurnRight();
                }
            }
        });
        final ToggleButton grabButton = (ToggleButton) findViewById(R.id.grabButton);
        grabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(grabButton.isChecked()) {
                    grabController.startGrab();
                } else {
                    grabController.stopGrab();
                }
            }
        });
        final ToggleButton releaseButton = (ToggleButton) findViewById(R.id.releaseButton);
        releaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(releaseButton.isChecked()) {
                    grabController.startGrabRelease();
                } else {
                    grabController.stopGrabRelease();
                }
            }
        });
        final Button beepButton = (Button) findViewById(R.id.beepButton);
        beepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communicationController.beep();
            }
        });
        final Button sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText text = (EditText) findViewById(R.id.editText);
                communicationController.sendMessage(text.getText().toString());
                text.setText("");
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
            setUpBluetooth();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
