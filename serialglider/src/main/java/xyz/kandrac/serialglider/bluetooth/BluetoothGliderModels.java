package xyz.kandrac.serialglider.bluetooth;

import java.util.HashMap;

import xyz.kandrac.serialglider.GliderModels;

/**
 * Based on {@code Map} of {@link BluetoothGliderDevice}s provides proper filtering of models from
 *
 * Created by jan on 19.12.2016.
 */
public class BluetoothGliderModels extends GliderModels<BluetoothGliderDevice> {

    public BluetoothGliderModels(HashMap<String, Class<? extends BluetoothGliderDevice>> supportedList) {
        super(supportedList);
    }
}
