package com.pjh.kotlinsample;

import androidx.core.text.StringKt;

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

    @Test
    public void testSingletonjava() {
        // 'SingletonJava()' has private access in 'com.pjh.kotlinsample.SingletonJava'
//        SingletonJava singletonJava = new SingletonJava();

        // 매번 비슷한 코드 작성하는 것이 번거로움
        SingletonJava sj = SingletonJava.getInstance();
        sj.log("hello jinbro");
    }

    @Test
    public void testFruit() {
        FruitJava fj = new FruitJava();
        fj.fruitName = "사과";
        fj.description = "맛있는 사과";
        System.out.println(fj);
    }

    @Test
    public void testFruitEquals() {
        FruitJava fj1 = new FruitJava();
        FruitJava fj2 = new FruitJava();

        fj1.fruitName = "바나나";
        fj2.fruitName = "바나나";

        fj1.description = "달콤한 바나나";
        fj2.description = "달콤한 바나나";

//        Assert.assertEquals(fj1, fj2);
        Assert.assertEquals(fj1.hashCode(), fj2.hashCode());
    }

    @Test
    public void testExtFunc() {
        // Java에서 Kotlin 확장 함수 호출 방법 동일
        String lastString = StringUtil.lastString("Kotlin JJANG");
        Assert.assertEquals("G", lastString);
    }

    @Test
    public void testNPE1() {
        NPE npe = new NPE();
        Assert.assertEquals(3, npe.strLen("abc"));
        Assert.assertEquals(0, npe.strLen(null));
    }

    @Test
    public void testNullType() {
        Assert.assertEquals(true, null instanceof String);
    }
}
