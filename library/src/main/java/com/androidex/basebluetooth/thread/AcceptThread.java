package com.androidex.basebluetooth.thread;

import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import com.androidex.basebluetooth.BluetoothChatHelper;
import com.androidex.basebluetooth.common.ChatConstant;
import com.androidex.basebluetooth.utils.BleLog;

import java.io.IOException;

/**
 * @Description: 监听线
 */
public class AcceptThread extends Thread {

    private BluetoothChatHelper mHelper;
    private final BluetoothServerSocket mServerSocket;
    private String mSocketType;

    public AcceptThread(BluetoothChatHelper bluetoothChatHelper, boolean secure) {
        mHelper = bluetoothChatHelper;
        BluetoothServerSocket tmp = null;
        mSocketType = secure ? "Secure" : "Insecure";

        try {
            if (secure) {
                tmp = mHelper.getAdapter().listenUsingRfcommWithServiceRecord(ChatConstant.NAME_SECURE, ChatConstant.UUID_SECURE);
            } else {
                tmp = mHelper.getAdapter().listenUsingInsecureRfcommWithServiceRecord(ChatConstant.NAME_INSECURE, ChatConstant.UUID_INSECURE);
            }
        } catch (IOException e) {
            BleLog.e("Socket Type: " + mSocketType + "listen() failed", e);
        }
        mServerSocket = tmp;
    }

    public void run() {
        BleLog.i("Socket Type: " + mSocketType + "BEGIN mAcceptThread" + this);
        setName("AcceptThread" + mSocketType);

        BluetoothSocket socket = null;

        while (mHelper.getState() != com.androidex.basebluetooth.common.State.STATE_CONNECTED) {
            try {
                BleLog.i("wait new socket:" + mServerSocket);
                socket = mServerSocket.accept();
            } catch (IOException e) {
                BleLog.e("Socket Type: " + mSocketType + " accept() failed", e);
                break;
            }
            if (socket != null) {
                synchronized (this) {
                    if(mHelper.getState() == com.androidex.basebluetooth.common.State.STATE_LISTEN
                            || mHelper.getState() == com.androidex.basebluetooth.common.State.STATE_CONNECTING){
                        BleLog.i("mark CONNECTING");
                        mHelper.connected(socket, socket.getRemoteDevice(), mSocketType);
                    } else if(mHelper.getState() == com.androidex.basebluetooth.common.State.STATE_NONE
                            || mHelper.getState() == com.androidex.basebluetooth.common.State.STATE_CONNECTED){
                        try {
                            socket.close();
                        } catch (IOException e) {
                            BleLog.e("Could not close unwanted socket", e);
                        }
                    }
                }
            }
        }
        BleLog.i("END mAcceptThread, socket Type: " + mSocketType);
    }

    public void cancel() {
        BleLog.i("Socket Type" + mSocketType + "cancel " + this);
        try {
            mServerSocket.close();
        } catch (IOException e) {
            BleLog.e("Socket Type" + mSocketType + "close() of server failed", e);
        }
    }
}
