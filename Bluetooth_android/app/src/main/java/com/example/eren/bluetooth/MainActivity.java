package com.example.eren.bluetooth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.widget.Button;
import java.util.Set;
//import java.util.ArrayList;
import android.widget.Toast;
import android.content.Context;
//import android.widget.ArrayAdapter;
//import android.widget.AdapterView;
//import android.view.View.OnClickListener;
import android.widget.TextView;
import android.content.Intent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.BluetoothServerSocket;
import android.util.Log;
import java.io.IOException;
import java.util.UUID;
import android.nfc.Tag;

public class MainActivity extends AppCompatActivity {

//    Button btn = (Button)findViewById(R.id.button);
//    private BluetoothAdapter myBluetooth = null;

    public static final java.lang.String arduinoModuleName = "HC-06";
    private int toastDuration = Toast.LENGTH_SHORT;

    public BluetoothDevice queryDevices(java.lang.String deviceToFind, BluetoothAdapter btAdapter){
        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();

        boolean isAMatchingDevice = false;
        BluetoothDevice matchingDevice = null;

        Context context = getApplicationContext(); //for Toast
        Toast toast = Toast.makeText(context,"Found Arduino Module", toastDuration);

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.

            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address


                if (deviceName.equals(deviceToFind)) {
                    isAMatchingDevice = true;
                    toast.show();
                    matchingDevice = device;
                }
            }

        }

        if(!isAMatchingDevice){
            toast = Toast.makeText(context, "No paired devices found", toastDuration);
            toast.show();
        }

        return matchingDevice;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView debug = (TextView)findViewById(R.id.text_box1);


        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice arduinoModule;





        if (!mBluetoothAdapter.isEnabled()) { //if bluetooth isn't enabled, enable it
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }

        arduinoModule = queryDevices(arduinoModuleName, mBluetoothAdapter); //set arduinoModule device to device with name "HC-06"


        debug.setText("Found arduino module: " + arduinoModule.getName() + "\n");
//        Log.d(arduinoModule.getUuids());

        Log.d("TAG", "Creating new Connection thread");

        ConnectThread newThread = new ConnectThread(arduinoModule);
        debug.append("Created connection thread: " + newThread.toString());



    }

}




