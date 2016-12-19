package xyz.kandrac.serialglider.usb;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import xyz.kandrac.serialglider.GliderRouter;

/**
 * Created by jan on 19.12.2016.
 */

public class UsbGliderRouter extends GliderRouter<UsbGliderDevice> {

    private static final String ACTION_USB_PERMISSION = UsbGliderRouter.class.getPackage().getName() + ".USB_PERMISSION";

    private UsbGliderModels modelsSupported;
    private UsbGliderDevice connecting;

    public UsbGliderRouter(HashMap<String, Class<? extends UsbGliderDevice>> devices) {
        modelsSupported = new UsbGliderModels(devices);
    }

    /**
     * Call this in your {@code Activity.onResume()} method in order to register receiver for usb
     * permission
     */
    public void onResume(Context context) {
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        context.registerReceiver(mUsbReceiver, filter);
    }

    /**
     * Call this in your {@code Activity.onResume()} method in order to unregister from listening
     * for usb permission
     */
    public void onPause(Context context) {
        context.unregisterReceiver(mUsbReceiver);
    }

    @Override
    public Set<UsbGliderDevice> getDevices(Context context) {
        Set<UsbGliderDevice> result = new HashSet<>();
        UsbManager manager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();

        for (UsbDevice device : deviceList.values()) {
            int vendorId = device.getVendorId();
            int productId = device.getProductId();

            UsbGliderDevice usbGliderDevice = modelsSupported.getDevice(String.format(Locale.ENGLISH, "%s:%s", vendorId, productId));

            if (usbGliderDevice == null) {
                continue;
            }
            usbGliderDevice.setUsbDevice(device);
            result.add(usbGliderDevice);
        }
        return result;
    }

    @Override
    public boolean check(@NonNull UsbGliderDevice device, Context context) {

        UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);

        if (!usbManager.hasPermission(device.getUsbDevice())) {
            connecting = device;
            usbManager.requestPermission(device.getUsbDevice(), PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0));
            return false;
        }

        return true;
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        // if device gets the permissions and it's device we required permission for
                        // connect to that device right away
                        if (device.equals(connecting.getUsbDevice())) {
                            connecting.connect(context);
                        }
                    }
                    connecting = null;
                }
            }
        }
    };
}
