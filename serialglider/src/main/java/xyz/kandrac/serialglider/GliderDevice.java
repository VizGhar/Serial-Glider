package xyz.kandrac.serialglider;

import android.content.Context;

/**
 * Every device that should be connected needs to support following methods:
 * <ol>
 * <li>connect</li>
 * <li>disconnect</li>
 * <li>handle received message</li>
 * <li>provide unique identifier per type</li>
 * <li>provide device name</li>
 * </ol>
 * <p>
 * Created by jan on 19.12.2016.
 */
public abstract class GliderDevice {

    protected GliderMessageListener mListener;

    /**
     * In order to properly handle message from any {@link GliderDevice} you will need to set this
     * listener for the device. The listener should only post message received events to
     * {@code Activity}'s main thread.
     */
    public interface GliderMessageListener {

        /**
         * Method called after device's serial interface receives its message. This message will
         * always be sent to main thread
         *
         * @param message to be processed
         */
        void onMessageReceived(String message);
    }

    /**
     * Set listener for message handling
     *
     * @param listener to be set
     */
    public void setListener(GliderMessageListener listener) {
        mListener = listener;
    }

    /**
     * Tries to connect this device for serial communication
     *
     * @param context for connection
     * @return true if connected
     */
    public abstract boolean connect(Context context);

    /**
     * Disconnect connected device if possible
     */
    public abstract void disconnect();

    /**
     * Based on input from handle message composed of {@code byte[]}.
     * <p>
     * If message is processed and ready to publish, {@code mListener.onMessageReceived()} is invoked
     *
     * @param message to handle
     */
    public abstract void handleMessage(byte[] message);

    /**
     * Returns identifier based on following formatting: "%s:%s:%s" with arguments Vendor ID,
     * Device ID, Product ID
     * <p>
     *
     * @return Identifier
     */
    public abstract String getIdentifier();

    /**
     * @return readable version of USB device identifier
     */
    public abstract String getReadableIdentifier();
}
