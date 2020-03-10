package com.example.coroutinetest

import kotlinx.coroutines.*
import org.junit.Test

import kotlin.system.measureTimeMillis

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun test(){

    }

    private suspend fun doSomethingUsefulOne():Int{
        delay(1000)
        return 1
    }

    private suspend fun doSomethingUsefulTwo():Int{
        delay(1000)
        return 2
    }

}
