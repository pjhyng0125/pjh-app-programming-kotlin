package com.pjh.kotlinsample;

import android.widget.Toast;

public class ToastUtilJava {

    // Activity 상속 필요
    public static void toastShort(String msg) {
        Toast.makeText(MainApplication.getAppContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(String msg) {
        Toast.makeText(MainApplication.getAppContext(), msg, Toast.LENGTH_LONG).show();
    }

    public static void toast(String msg, int length) {
        Toast.makeText(MainApplication.getAppContext(), msg, length).show();
    }

    // 파라미터 기본값 설정을 위한 메소드 오버로딩
    public static void toast(String msg) {
        toast(msg, Toast.LENGTH_SHORT);
    }
}
