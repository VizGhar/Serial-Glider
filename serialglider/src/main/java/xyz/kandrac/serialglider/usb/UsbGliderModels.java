package xyz.kandrac.serialglider.usb;

import java.util.HashMap;

import xyz.kandrac.serialglider.GliderModels;

/**
 * Created by jan on 19.12.2016.
 */

public class UsbGliderModels extends GliderModels<UsbGliderDevice> {

    public UsbGliderModels(HashMap<String, Class<? extends UsbGliderDevice>> supportedList) {
        super(supportedList);
    }
}
