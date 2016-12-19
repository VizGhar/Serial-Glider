package xyz.kandrac.serialglider.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import xyz.kandrac.serialglider.GliderRouter;

/**
 * Created by jan on 19.12.2016.
 */

public class BluetoothGliderRouter extends GliderRouter<BluetoothGliderDevice> {

    private BluetoothGliderModels modelsSupported;

    public BluetoothGliderRouter(HashMap<String, Class<? extends BluetoothGliderDevice>> devices) {
        if (devices != null) {
            modelsSupported = new BluetoothGliderModels(devices);
        }
    }

    @Override
    public Set<BluetoothGliderDevice> getDevices(Context context) {

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            return null;
        }

        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        Set<BluetoothGliderDevice> result = new HashSet<>();

        // filter bluetooth devices
        for (BluetoothDevice device : bondedDevices) {
            String identifier = device.getName();
            BluetoothGliderDevice bluetoothGliderDevice;
            if (modelsSupported != null) {
                bluetoothGliderDevice = modelsSupported.getDevice(identifier);
            } else {
                bluetoothGliderDevice = new BluetoothGliderDeviceMinimal();
            }

            if (bluetoothGliderDevice == null) {
                continue;
            }

            bluetoothGliderDevice.setBluetoothDevice(device);

            result.add(bluetoothGliderDevice);
        }

        return result;
    }

    @Override
    public boolean check(@NonNull BluetoothGliderDevice device, Context context) {
        return true;
    }

}
