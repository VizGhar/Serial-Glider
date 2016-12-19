package xyz.kandrac.serialglider;

import android.content.Context;
import android.hardware.usb.UsbManager;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Set;

/**
 * Router should work as helper to simplify:
 * <ul>
 * <li>getting devices of specific type,</li>
 * <li>connecting to device,</li>
 * <li>handling connection with the device,</li>
 * <li>reading from device,</li>
 * <li>writing to device and</li>
 * <li>disconnecting device</li>
 * </ul>
 * <p>
 * Router should also gently handle problems with connections
 * <p>
 * This Router class is abstract so it is not entangled with specific connection type
 * (bluetooth, wireless, USB ...) in order to support any of these connection types please extend
 * this class
 * <p>
 * Created by jan on 19.12.2016.
 */
public abstract class GliderRouter<T extends GliderDevice> {

    public static final int CONNECT_RESULT_ALREADY_CONNECTED = 1;
    public static final int CONNECT_RESULT_CANNOT_CONNECT = 2;
    public static final int CONNECT_RESULT_CHECK_FAIL = 3;
    public static final int CONNECT_RESULT_CONNECTED = 4;

    private T connectedDevice;

    /**
     * List of all models supported or null if any model is supported. In case of null, keep in mind
     * you will have to create some generic device, that will handle all the requests
     */
    protected GliderModels<? extends T> modelsSupported;

    public GliderRouter(HashMap<String, Class<? extends T>> devices) {
        if (devices != null) {
            modelsSupported = new GliderModels<>(devices);
        }
    }

    /**
     * Helper method for getting all {@link GliderDevice}s from given context. Basically every time
     * we want to search for some device we have to search in device context.
     *
     * @param context to get devices from
     * @return list of all devices
     * @see {@link UsbManager#getDeviceList()}
     */
    public abstract Set<T> getDevices(Context context);

    /**
     * Check whether connecting to device is even possible. If connection is either try to make it
     * possible right away in this method, or {@link #connectDevice(GliderDevice, Context)} will
     * return {@link #CONNECT_RESULT_CHECK_FAIL}
     *
     * @return true if connection is possible
     */
    public abstract boolean check(@NonNull T device, Context context);

    /**
     * Connect to device obtained via {@link #getDevices(Context)} method
     *
     * @param device to connect to
     * @return status of connecting process
     */
    public int connectDevice(@NonNull T device, Context context) {
        if (connectedDevice != null) {
            return CONNECT_RESULT_ALREADY_CONNECTED;
        }

        if (!check(device, context)) {
            return CONNECT_RESULT_CHECK_FAIL;
        }

        if (device.connect(context)) {
            connectedDevice = device;
            return CONNECT_RESULT_CONNECTED;
        } else {
            return CONNECT_RESULT_CANNOT_CONNECT;
        }
    }

    /**
     * Disconnect device that was previously connected via {@link #connectDevice(GliderDevice, Context)}
     */
    public void disconnectDevice() {
        if (connectedDevice != null) {
            connectedDevice.disconnect();
        }
    }
}
