package xyz.kandrac.serial;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.Set;

import xyz.kandrac.serialglider.GliderDevice;
import xyz.kandrac.serialglider.bluetooth.BluetoothGliderDevice;
import xyz.kandrac.serialglider.bluetooth.BluetoothGliderRouter;

public class MainActivity extends AppCompatActivity implements GliderDevice.MessageReaderListener {

    BluetoothGliderRouter router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        router = new BluetoothGliderRouter(null);
        final Set<BluetoothGliderDevice> devices = router.getDevices(this);

        new AlertDialog.Builder(this)
                .setSingleChoiceItems(new DevicesAdapter(devices, this), 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        BluetoothGliderDevice device = (BluetoothGliderDevice) (devices.toArray())[i];
                        device.setListener(MainActivity.this);
                        router.connectDevice(device, MainActivity.this);
                        dialogInterface.dismiss();
                    }
                })
                .show();
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
