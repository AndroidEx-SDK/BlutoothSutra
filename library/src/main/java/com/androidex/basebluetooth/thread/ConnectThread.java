package com.androidex.basebluetooth.thread;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.androidex.basebluetooth.common.ChatConstant;
import com.androidex.basebluetooth.BluetoothChatHelper;
import com.androidex.basebluetooth.utils.BleLog;

import java.io.IOException;

/**
 * @Description: 连接线程
 */
public class ConnectThread extends Thread {

    private BluetoothChatHelper mHelper;
    private final BluetoothSocket mSocket;
    private final BluetoothDevice mDevice;
    private String mSocketType;

    public ConnectThread(BluetoothChatHelper bluetoothChatHelper, BluetoothDevice device, boolean secure) {
        mHelper = bluetoothChatHelper;
        mDevice = device;
        BluetoothSocket tmp = null;
        mSocketType = secure ? "Secure" : "Insecure";

        try {
            if (secure) {
                tmp = device.createRfcommSocketToServiceRecord(ChatConstant.UUID_SECURE);
            } else {
                tmp = device.createInsecureRfcommSocketToServiceRecord(ChatConstant.UUID_INSECURE);
            }
        } catch (IOException e) {
            BleLog.e("Socket Type: " + mSocketType + "create() failed", e);
        }
        mSocket = tmp;
    }

    public void run() {
        BleLog.i("BEGIN mConnectThread SocketType:" + mSocketType);
        setName("ConnectThread" + mSocketType);

        mHelper.getAdapter().cancelDiscovery();

        try {
            mSocket.connect();
        } catch (IOException e) {
            try {
                mSocket.close();
            } catch (IOException e2) {
                BleLog.e("unable to close() " + mSocketType + " socket during connection failure", e2);
            }
            mHelper.connectionFailed();
            return;
        }

        synchronized (this) {
            mHelper.setConnectThread(null);
        }

        mHelper.connected(mSocket, mDevice, mSocketType);
    }

    public void cancel() {
        try {
            mSocket.close();
        } catch (IOException e) {
            BleLog.e("close() of connect " + mSocketType
                    + " socket failed", e);
        }
    }
}
