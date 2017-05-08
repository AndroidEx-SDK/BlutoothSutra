package com.androidex.basebluetooth.callback;

import com.androidex.basebluetooth.common.State;

/**
 * @Description: 消息回调
 */
public interface IChatCallback<T> {
    void connectStateChange(State state);
    void writeData(T data, int type);
    void readData(T data, int type);
    void setDeviceName(String name);
    void showMessage(String message, int code);
}
