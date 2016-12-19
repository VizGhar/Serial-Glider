package xyz.kandrac.serial.bluetooth.scaner;

import xyz.kandrac.serialglider.bluetooth.BluetoothGliderDevice;

/**
 * Created by jan on 19.12.2016.
 */

public class BluetoothSensodroidScaner extends BluetoothGliderDevice {

    public static final String READABLE_IDENTIFIER = "Sensodroid Barcode Reader";
    public static final String IDENTIFIER = "symcode";

    private StringBuilder cached = new StringBuilder();

    public BluetoothSensodroidScaner() {

    }

    @Override
    public void handleMessage(byte[] message) {

        for (byte current : message) {
            if (current >= '0' && current <= '9') {
                cached.append(current - '0');
            } else {
                getListener().onMessageReceived(cached.toString());
                cached = new StringBuilder();
            }
        }
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public String getReadableIdentifier() {
        return READABLE_IDENTIFIER;
    }
}
