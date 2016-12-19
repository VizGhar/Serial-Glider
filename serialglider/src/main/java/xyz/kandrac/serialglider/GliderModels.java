package xyz.kandrac.serialglider;

import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Holds reference to every {@link GliderDevice} supported by {@link GliderRouter}. If any new
 * device needs to be supported follow these instructions:
 * <ol>
 * <li>Create new {@link GliderDevice} class</li>
 * <li>Add reference to that {@code GliderDevice} into {@code SupportedList} passed in constructor</li>
 * </ol>
 * Created by jan on 19.12.2016.
 */
public class GliderModels<T extends GliderDevice> {

    private static final String TAG = GliderModels.class.getName();

    private HashMap<String, Class<? extends T>> mSupportedList;

    public GliderModels(HashMap<String, Class<? extends T>> supportedList) {
        mSupportedList = supportedList;
    }

    /**
     * Get instantiated device based on its identifier
     *
     * @return instance of {@code UsbGlider} or null if undefined
     */
    @Nullable
    public T getDevice(String identifier) {

        try {
            // instantiate UsbGlider if its identifier is found in mSupportedList
            Class<? extends T> deviceClass = mSupportedList.get(identifier);
            if (deviceClass != null) {
                return deviceClass.getConstructor().newInstance();
            }
            return null;
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "No constructor error creating instance of class for " + identifier, e);
            return null;
        } catch (InstantiationException e) {
            Log.e(TAG, "Instantiation error while creating instance of class for " + identifier, e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Access error while creating instance of class for " + identifier, e);
        } catch (InvocationTargetException e) {
            Log.e(TAG, "Invocation error while creating instance of class for " + identifier, e);
        }
        return null;
    }
}
