package xyz.kandrac.serial.usb;

import java.util.HashMap;

import xyz.kandrac.serial.usb.scaner.UsbSensodroidScaner;
import xyz.kandrac.serialglider.usb.UsbGliderDevice;

/**
 * List of all supported USB scanners
 * <p>
 * Created by jan on 19.12.2016.
 */
public final class ScanerFilter {

    private ScanerFilter() {

    }

    private static HashMap<String, Class<? extends UsbGliderDevice>> mSupportedList = new HashMap<>();

    static {
        mSupportedList.put(UsbSensodroidScaner.IDENTIFIER, UsbSensodroidScaner.class);
    }

}
