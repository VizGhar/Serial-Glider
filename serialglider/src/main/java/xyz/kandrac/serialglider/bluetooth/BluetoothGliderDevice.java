package xyz.kandrac.serialglider.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

import xyz.kandrac.serialglider.GliderDevice;

/**
 * Standard serial Bluetooth communicator
 * <p>
 * Created by jan on 19.12.2016.
 */

public abstract class BluetoothGliderDevice extends GliderDevice {

    private BluetoothSocket mSocket;
    private InputStream mmInputStream;
    private Thread workerThread;
    private boolean stopWorker;

    private BluetoothDevice bluetoothDevice;

    @Override
    public boolean connect(Context context) {

        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID

        try {
            mSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
            mSocket.connect();

            mmInputStream = mSocket.getInputStream();

            beginListenForData();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    private void beginListenForData() {
        final Handler handler = new Handler(Looper.getMainLooper());
        stopWorker = false;


        workerThread = new Thread(new Runnable() {
            public void run() {
                BufferedReader reader = new BufferedReader(new InputStreamReader(mmInputStream));

                while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                    try {

                        final String line = reader.readLine();

                        handler.post(new Runnable() {
                            public void run() {
                                handleMessage(line.getBytes());
                            }
                        });

                    } catch (IOException ex) {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }

    @Override
    public void disconnect() {
        stopWorker = true;
        try {
            mmInputStream.close();
            mSocket.close();
            workerThread.interrupt();
        } catch (Exception ex) {

        }
    }


    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public BluetoothDevice getUsbDevice() {
        return bluetoothDevice;
    }
}
