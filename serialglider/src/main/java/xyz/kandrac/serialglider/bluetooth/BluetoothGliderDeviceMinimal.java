package xyz.kandrac.serialglider.bluetooth;

/**
 * Created by jan on 19.12.2016.
 */

public class BluetoothGliderDeviceMinimal extends BluetoothGliderDevice {
    @Override
    public void handleMessage(byte[] message) {
        getListener().onMessageReceived(new String(message));
    }

    @Override
    public String getIdentifier() {
        return null;
    }

    @Override
    public String getReadableIdentifier() {
        return null;
    }
}
