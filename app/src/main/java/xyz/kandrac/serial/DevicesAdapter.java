package xyz.kandrac.serial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Set;

import xyz.kandrac.serialglider.bluetooth.BluetoothGliderDevice;

/**
 * Created by jan on 19.12.2016.
 */

public class DevicesAdapter extends BaseAdapter {

    private final Context context;
    Set<BluetoothGliderDevice> items;

    public DevicesAdapter(Set<BluetoothGliderDevice> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.toArray()[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BluetoothGliderDevice item = (BluetoothGliderDevice) getItem(i);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View result = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
        ((TextView) result.findViewById(android.R.id.text1)).setText(item.getBluetoothDevice().getName());
        return result;
    }
}
