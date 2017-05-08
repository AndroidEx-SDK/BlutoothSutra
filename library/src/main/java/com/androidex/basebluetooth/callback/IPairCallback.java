package com.androidex.basebluetooth.callback;

/**
 * @Description: 配对回调
 */
public interface IPairCallback {
    void unBonded();
    void bonding();
    void bonded();
    void bondFail();
}
