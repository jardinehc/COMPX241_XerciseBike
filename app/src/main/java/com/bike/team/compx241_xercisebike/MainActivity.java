package com.bike.team.compx241_xercisebike;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;



public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_ENABLE_BT = 1;

    private OutputStream outputStream;
    private InputStream inStream;

    private Integer position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            startBluetooth();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startBluetooth() throws IOException {

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device doesn't support Bluetooth
        } else {

            if (mBluetoothAdapter.isEnabled()) {
                Set<BluetoothDevice> bondedDevices = mBluetoothAdapter.getBondedDevices();

                if(bondedDevices.size() > 0) {
                    BluetoothDevice device = null;

                    for(BluetoothDevice tdevice : bondedDevices)
                    {
                        if(tdevice.getName().equals("ubuntu-0"))
                        {
                            device = tdevice;
                            break;
                        }
                    }
                    if (device == null) {
                        System.out.println("failed");
                        return;
                    }
//                    Object[] devices = (Object []) bondedDevices.toArray();
//                    System.out.println(devices.length);
//                    BluetoothDevice device = (BluetoothDevice) devices[position];
                    ParcelUuid[] uuids = device.getUuids();
                    BluetoothSocket socket = device.createRfcommSocketToServiceRecord(uuids[0].getUuid());
                    //socket.connect();
                    //outputStream = socket.getOutputStream();
                    //inStream = socket.getInputStream();
                }

                Log.e("error", "No appropriate paired devices.");
            } else {
                // bluetooth disabled

                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
    }

    public void write(String s) throws IOException {
        outputStream.write(s.getBytes());
    }

    public void run() {
        final int BUFFER_SIZE = 1024;
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytes = 0;
        int b = BUFFER_SIZE;

        while (true) {
            try {
                bytes = inStream.read(buffer, bytes, BUFFER_SIZE - bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
