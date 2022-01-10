package com.pjh.kotlinsample

import org.junit.Assert
import org.junit.Test

class KotlinTest {
    @Test
    fun test1() {
        Assert.assertEquals(4, 2+2)
    }

    @Test
    fun testVOGetSetKotlin() {
        val personK = PersonKotlin("jinbro")
        personK.age= 29
        Assert.assertEquals(29, personK.age)
        Assert.assertEquals("jinbro", personK.name)
    }

    @Test
    fun testSetNickname() {
        val personK = PersonKotlin("jinbro")
        personK.nickname = "Banana"
        Assert.assertEquals("banana", personK.nickname)
    }

    @Test
    fun testDelegateProperty() {
        val user = User()
        user.nickname = "small Big"
        Assert.assertEquals("SMALL BIG", user.nickname)
    }

    @Test
    fun testDelegatePropertyVal() {
        val user = User()
        // Val cannot be reassigned
//        user.nickvalue = "small Big"
    }

    @Test
    fun testLazyInit() {
        val user = User()
        println("not init")
        Assert.assertNotNull(user.httpText)
    }

    @Test
    fun testLazyInitObserver() {
        val user = User()
        user.name = "jinbro"
        user.name = "park"
    }

    @Test
    fun testDelegateMap() {
        val animal = Animal(mutableMapOf(
            "name" to "cat",
            "age" to 20
        ))

        Assert.assertEquals("cat", animal.name)
        Assert.assertEquals(20, animal.age)

        animal.age = 21
        animal.name = "dog"

        Assert.assertEquals("dog", animal.map["name"])
        Assert.assertEquals(21, animal.map["age"])
    }

    @Test
    fun testSingletonKotlin() {
        SingletonKotlin.log("hello jinbro (kotlin)")
    }

    @Test
    fun testFruit() {
        val f1 = Fruit("바나나", "긴 바나나")
        val f2 = Fruit("바나나", "긴 바나나")

        println(f1)
        println(f2)

        Assert.assertEquals(f1, f2)
        Assert.assertEquals(f1.hashCode(), f2.hashCode())
    }
}