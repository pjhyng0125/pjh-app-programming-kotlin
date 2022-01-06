package com.pjh.kotlinsample;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JavaTest {

    @Test
    public void test1() {
        Assert.assertEquals(4, 2+2);
    }

    @Test
    public void testVOGetSetJava() {
        PersonJava personJ = new PersonJava("jinbro");
        personJ.setAge(29);

        // 자바의 경우, lombok 라이브러리 활용 가능
        Assert.assertEquals(29, personJ.getAge());
        Assert.assertEquals("jinbro", personJ.getName());

        // 코틀린 객체 생성
        PersonKotlin personK = new PersonKotlin("jinbro");
        personK.setAge(29);

        // 코틀린 객체 인스턴스 get/set 정상 확인
        Assert.assertEquals(29, personK.getAge());
        Assert.assertEquals("jinbro", personK.getName());
    }

    @Test
    public void testSetNickname() {
        PersonJava personJ = new PersonJava("jinbro");
        personJ.setNickname("Apple");
        Assert.assertEquals("apple", personJ.getNickname());

        PersonKotlin personK = new PersonKotlin("jinbro");
        personK.setNickname("Apple");
        Assert.assertEquals("apple", personK.getNickname());
    }
}
