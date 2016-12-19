package xyz.kandrac.serial.bluetooth;

import java.util.HashMap;

import xyz.kandrac.serial.bluetooth.scaner.BluetoothSensodroidScaner;
import xyz.kandrac.serialglider.bluetooth.BluetoothGliderDevice;

/**
 * List of all supported Bluetooth scanners
 * <p>
 * Created by jan on 19.12.2016.
 */
public final class ScanerFilter {

    private ScanerFilter() {

    }

    private static HashMap<String, Class<? extends BluetoothGliderDevice>> mSupportedList = new HashMap<>();

    static {
        mSupportedList.put(BluetoothSensodroidScaner.IDENTIFIER, BluetoothSensodroidScaner.class);
    }

}
