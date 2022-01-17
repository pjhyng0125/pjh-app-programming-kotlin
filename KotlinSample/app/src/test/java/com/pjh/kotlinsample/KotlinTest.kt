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

    @Test
    fun testLambdaEx() {
        println(sum(1, 2))
        Assert.assertEquals(4, sum(1,3))
        Assert.assertEquals(4, { x: Int, y: Int -> x * y }(2, 2))
        val exp = { x: Int, y: Int ->
            { z: Int -> (x + y) * z}
        }
        val exp2 = exp(3, 2) // {(3+2)*z}
        val result = exp2(4) // {(3+2)*4}
        Assert.assertEquals(20, result)
    }

    @Test
    fun testCollectionApi() {
        val list = listOf(1, "2", 3, 4, 5.7, 1, 2)
        println(list.filter { item -> item is Int })
        println(list.filter {it is Int}) // 키워드가 1개인 경우, it 키워드로 접근 가능
        println(list.map { "value: ${it}" }) // 새로운 Collection 생성
        println(list.filter {it is Int}.map { "value2: ${it}" })
        println(list.find {it is Double})
        val map = list.groupBy { it.javaClass } // 그룹화 -> Map<String, List<T>>
        println(map)
        val list2 = listOf(listOf(1,2), listOf("1",99.0)) // 중첩 Collection
        println(list2)
        println(list2.map { "value3: ${it}" })
        println(list2.flatMap {it.toList()}) // 리스트 평평하게 하여 배열 반환
    }
}