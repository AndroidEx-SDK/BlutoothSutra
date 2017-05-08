package com.androidex.bluetooth.adapter;

import android.content.Context;
import android.widget.TextView;

import com.androidex.basebluetooth.common.ChatConstant;
import com.androidex.basebluetooth.mode.FileMessage;
import com.androidex.bluetooth.R;
import com.androidex.bluetooth.helper.HelperAdapter;
import com.androidex.bluetooth.helper.HelperViewHolder;
import com.androidex.bluetooth.mode.ChatInfo;

/**
 * @Description:
 */
public class ChatAdapter extends HelperAdapter<ChatInfo> {
    public ChatAdapter(Context context) {
        super(context, R.layout.item_chat_info_left, R.layout.item_chat_info_right);
    }

    @Override
    public void HelpConvert(HelperViewHolder viewHolder, int position, ChatInfo chatInfo) {
        if (chatInfo == null) {
            return;
        }
        TextView timeTv;
        TextView msgTv;
        TextView nameTv;
        if(chatInfo.isSend()){
            timeTv = viewHolder.getView(R.id.item_chat_right_time);
            msgTv = viewHolder.getView(R.id.item_chat_right_msg);
            nameTv = viewHolder.getView(R.id.item_chat_right_name);
            timeTv.setText(chatInfo.getSendTime());
        } else{
            timeTv = viewHolder.getView(R.id.item_chat_left_time);
            msgTv = viewHolder.getView(R.id.item_chat_left_msg);
            nameTv = viewHolder.getView(R.id.item_chat_left_name);
            timeTv.setText(chatInfo.getReceiveTime());
        }
        if(chatInfo.getMessage() != null){
            if(chatInfo.getMessage().getMsgType() == ChatConstant.VISE_COMMAND_TYPE_FILE){
                if(chatInfo.isSend()){
                    msgTv.setText("发送文件："+((FileMessage)chatInfo.getMessage()).getFileName());
                } else{
                    msgTv.setText("接收文件："+((FileMessage)chatInfo.getMessage()).getFileName());
                }
            } else{
                msgTv.setText(chatInfo.getMessage().getMsgContent());
            }
        }
        if(chatInfo.getFriendInfo() != null){
            nameTv.setText(chatInfo.getFriendInfo().getFriendNickName());
        }
    }

    @Override
    public int checkLayout(int position, ChatInfo item) {
        if(item != null && item.isSend()){
            return 1;
        }
        return 0;
    }
}
