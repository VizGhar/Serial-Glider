package xyz.kandrac.serialglider.usb;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;

import xyz.kandrac.serialglider.GliderDevice;

/**
 * Usb Device Decorator class for handling serial communication.
 * <p>
 * Created by jan on 19.12.2016.
 */
public abstract class UsbGliderDevice extends GliderDevice {

    private UsbDeviceConnection deviceConnection;
    private Handler mHandler;
    private UsbDevice usbDevice;
    private UsbSerialDevice serial;

    @Override
    public boolean connect(Context context) {

        if (deviceConnection == null) {
            UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);

            // open connection to device
            deviceConnection = usbManager.openDevice(usbDevice);
        }

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                UsbGliderDevice.this.handleMessage((byte[]) (msg.obj));
            }
        };


        serial = UsbSerialDevice.createUsbSerialDevice(usbDevice, deviceConnection);

        serial.open();
        serial.read(new UsbSerialInterface.UsbReadCallback() {
            @Override
            public void onReceivedData(byte[] bytes) {
                mHandler.obtainMessage(0, bytes).sendToTarget();
            }
        });

        return true;
    }

    @Override
    public void disconnect() {
        if (serial != null) {
            serial.close();
        }
    }

    public void setUsbDevice(UsbDevice usbDevice) {
        this.usbDevice = usbDevice;
    }

    public UsbDevice getUsbDevice() {
        return usbDevice;
    }

    /**
     * @return identifier composed as "<Vendor ID>:<Product ID>"
     */
    @Override
    public abstract String getIdentifier();
}
