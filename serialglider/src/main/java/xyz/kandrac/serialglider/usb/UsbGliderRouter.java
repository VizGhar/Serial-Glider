package xyz.kandrac.serialglider.usb;

import android.content.Context;

import java.util.List;

import xyz.kandrac.serialglider.GliderRouter;

/**
 * Created by jan on 19.12.2016.
 */

public class UsbGliderRouter extends GliderRouter<UsbGliderDevice> {


    @Override
    public List<UsbGliderDevice> getDevices(Context context) {
        return null;
    }

    @Override
    public boolean check() {
        return false;
    }
}
