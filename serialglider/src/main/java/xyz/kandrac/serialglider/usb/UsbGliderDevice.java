package xyz.kandrac.serialglider.usb;

import android.content.Context;

import xyz.kandrac.serialglider.GliderDevice;

/**
 * Created by jan on 19.12.2016.
 */

public class UsbGliderDevice extends GliderDevice {
    @Override
    public boolean connect(Context context) {
        return false;
    }

    @Override
    public void disconnect() {

    }

    @Override
    public void handleMessage(byte[] message) {

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
