package xyz.kandrac.serialglider.usb;

import android.content.Context;
import android.hardware.usb.UsbDevice;

import xyz.kandrac.serialglider.GliderDevice;

/**
 * Created by jan on 19.12.2016.
 */

public abstract class UsbGliderDevice extends GliderDevice {

    private UsbDevice usbDevice;

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

    public void setUsbDevice(UsbDevice usbDevice) {
        this.usbDevice = usbDevice;
    }

    public UsbDevice getUsbDevice() {
        return usbDevice;
    }
}
