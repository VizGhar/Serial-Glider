package xyz.kandrac.serialglider;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.util.Set;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by jan on 19.12.2016.
 */

public class BluetoothGliderRouter {

    @Retention(SOURCE)
    @IntDef({})
    public @interface FindBtResult{}

    /**
     * Lookup for bound devices. If
     *
     * @param activity to start Bluetooth enable activity from
     * @return bound devices or null if Bluetooth not supported or disabled
     */
    public static Set<BluetoothDevice> findBTDevices(Activity activity) {

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            // Bluetooth not supported
            return null;
        }

        if (!bluetoothAdapter.isEnabled()) {
            // Bluetooth disabled
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBluetooth, 0);
            return null;
        }

        // list of bond devices
        return bluetoothAdapter.getBondedDevices();
    }


}
