package com.pjh.kotlinsample;

import android.app.Application;
import android.content.Context;

// Application 상속
// Application : 앱이 실행될 때 가장 먼저 실행, 한 개 instance만 존재
// 앱 전역적 사용 상태 정보 관리 기본 클래스
public class MainApplication extends Application {
    private static Context applicationContext;

    public static Context getAppContext() {
        return applicationContext;
    }

    // 앱 최초 실행 시 호출

    @Override
    public void onCreate() {
        super.onCreate();

        // static으로 선언된 applicationContext 에 현재 실행 중인 application 바인딩
        applicationContext = getApplicationContext();
    }
}
