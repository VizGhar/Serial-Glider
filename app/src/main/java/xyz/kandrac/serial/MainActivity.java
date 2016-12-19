package xyz.kandrac.serial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.Set;

import xyz.kandrac.serial.usb.ScanerFilter;
import xyz.kandrac.serialglider.GliderDevice;
import xyz.kandrac.serialglider.usb.UsbGliderDevice;
import xyz.kandrac.serialglider.usb.UsbGliderRouter;

public class MainActivity extends AppCompatActivity implements GliderDevice.GliderMessageListener {

    UsbGliderRouter router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        router = new UsbGliderRouter(ScanerFilter.getSupportedList());

        Set<UsbGliderDevice> devices = router.getDevices(this);

        for (UsbGliderDevice device : devices) {
            device.setListener(this);
            router.connectDevice(device, this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        router.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        router.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        router.disconnectDevice();
    }

    @Override
    public void onMessageReceived(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
