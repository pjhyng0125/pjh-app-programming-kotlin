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
}