package xyz.kandrac.serial.usb.rfid;

import java.util.Locale;

import xyz.kandrac.serialglider.usb.UsbGliderDevice;

/**
 * Created by jan on 19.12.2016.
 */

public class UsbJablotronReader extends UsbGliderDevice {

    private static final int VENDOR_ID = 0x16d6;
    private static final int PRODUCT_ID = 12;

    private StringBuilder cached = new StringBuilder();

    private static final String READABLE_IDENTIFIER = "Jablotron RFID reader";
    public static final String IDENTIFIER = String.format(Locale.ENGLISH, "%s:%s", VENDOR_ID, PRODUCT_ID);

    @Override
    public void handleMessage(byte[] message) {
        for (byte current : message) {
            if (current >= '0' && current <= '9') {
                cached.append(current - '0');
            } else {
                mListener.onMessageReceived(cached.toString());
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
