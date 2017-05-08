package com.androidex.basebluetooth.callback;

import java.util.List;

/**
 * @Description: 扫描回调
 */
public interface IScanCallback<T> {
    void discoverDevice(T t);
    void scanTimeout();
    void scanFinish(List<T> tList);
}
