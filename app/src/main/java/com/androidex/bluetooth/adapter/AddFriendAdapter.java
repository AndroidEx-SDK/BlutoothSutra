package com.androidex.bluetooth.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidex.bluetooth.R;
import com.androidex.bluetooth.helper.HelperAdapter;
import com.androidex.bluetooth.helper.HelperViewHolder;
import com.androidex.utils.common.StringUtil2;


/**
 * @Description:
 */
public class AddFriendAdapter extends HelperAdapter<BluetoothDevice> {
    public AddFriendAdapter(Context context) {
        super(context, R.layout.item_friend_info);
    }

    @Override
    public void HelpConvert(HelperViewHolder viewHolder, int position, BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return;
        }
        ImageView iconIv = viewHolder.getView(com.androidex.bluetooth.R.id.item_friend_icon);
        TextView nameTv = viewHolder.getView(com.androidex.bluetooth.R.id.item_friend_name);
        TextView addressTv = viewHolder.getView(com.androidex.bluetooth.R.id.item_friend_address);
        TextView statusTv = viewHolder.getView(com.androidex.bluetooth.R.id.item_friend_status);
        iconIv.setVisibility(View.GONE);
        statusTv.setVisibility(View.GONE);
        if(!StringUtil2.isNullOrEmpty(bluetoothDevice.getName())){
            nameTv.setText(bluetoothDevice.getName());
        } else{
            nameTv.setText(mContext.getString(com.androidex.bluetooth.R.string.device_unknown));
        }
        addressTv.setText(bluetoothDevice.getAddress());
    }
}
