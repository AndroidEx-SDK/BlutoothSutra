package com.androidex.utils.cipher;

/**
 * @Description: Base64加密解密
 *
 */
public class Base64 {

    public static byte[] encode(byte[] plain) {
        return android.util.Base64.encode(plain, android.util.Base64.DEFAULT);
    }

    public static String encodeToString(byte[] plain) {
        return android.util.Base64.encodeToString(plain, android.util.Base64.DEFAULT);
    }

    public static byte[] decode(String text) {
        return android.util.Base64.decode(text, android.util.Base64.DEFAULT);
    }

    public static byte[] decode(byte[] text) {
        return android.util.Base64.decode(text, android.util.Base64.DEFAULT);
    }
}
