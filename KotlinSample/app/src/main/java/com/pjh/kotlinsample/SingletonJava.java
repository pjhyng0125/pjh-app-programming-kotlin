package com.pjh.kotlinsample;

public class SingletonJava {
    // 생성자 private 감춤
    private SingletonJava() {}

    // 시작할 때 초기화 (쓰레드 동기화 방법 등 다른 방법도 있음)
    private static SingletonJava instance = new SingletonJava();

    // 생성된 instance를 외부에서 접근할 수 있는 방법 제공
    public static SingletonJava getInstance() {
        return instance;
    }

    public void log(String text) {
        System.out.println(text);
    }
}
