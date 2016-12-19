package xyz.kandrac.serialglider.usb;

/**
 * Minimal representation of serial USB device
 * <p>
 * Created by jan on 19.12.2016.
 */
public class UsbGliderDeviceMinimal extends UsbGliderDevice {

    @Override
    public void handleMessage(byte[] message) {
        mListener.onMessageReceived(new String(message));
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
