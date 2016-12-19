package xyz.kandrac.serialglider;

import android.content.Context;

/**
 * Every device that should be connected needs to support following methods:
 * <ol>
 *     <li>connect</li>
 *     <li>disconnect</li>
 *     <li>handle received message</li>
 *     <li>provide unique identifier per type</li>
 *     <li>provide device name</li>
 * </ol>
 *
 * Created by jan on 19.12.2016.
 */
public abstract class GliderDevice {

    private MessageReaderListener mListener;

    public interface MessageReaderListener {
        void onMessageReceived(String message);
    }

    public void setListener(MessageReaderListener listener) {
        mListener = listener;
    }

    public MessageReaderListener getListener() {
        return mListener;
    }

    public abstract boolean connect(Context context);

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
