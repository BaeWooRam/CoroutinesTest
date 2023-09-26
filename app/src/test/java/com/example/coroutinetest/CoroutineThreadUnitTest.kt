package com.example.coroutinetest

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import org.junit.Test
import java.util.Objects
import java.util.concurrent.TimeUnit

import kotlin.system.measureTimeMillis

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CoroutineThreadUnitTest {
    private val reps = 100

    @Test
    fun test(){

    }

    @Test
    fun `runInThread_테스트`(){
        runBlocking {
            println("runInThread_테스트 before" +
                    " Active Thread = ${Thread.activeCount()}")
            val time = measureTimeMillis {
                val jobs = ArrayList<Thread>()
                repeat(reps) {
                    jobs += Thread {
                        Thread.sleep(1000L)
                    }.also { it.start() }
                }
                println("runInThread_테스트 Active Thread = ${Thread.activeCount()}")
                jobs.forEach {
                    it.join()
                }
            }
            println("runInThread_테스트 Time: $time ms\n")
        }
    }

    @Test
    fun `runInRxjava_테스트`(){
        runBlocking {
            println("runInRxjava_테스트 before Active Thread = ${Thread.activeCount()}")
            val disposable = CompositeDisposable()
            val time = measureTimeMillis {
                val jobs = ArrayList<ObservableSource<Int>>()
                repeat(reps - 1) {
                    jobs += Observable.just(1).delay(1L, TimeUnit.SECONDS).subscribeOn(Schedulers.newThread())
                }
                val job = Observable.mergeArray(
                  *jobs.toTypedArray()
                ).subscribeOn(Schedulers.newThread())
                job.blockingSubscribe()
                println("runInRxjava_테스트 Active Thread = ${Thread.activeCount()}")
            }
            println("runInRxjava_테스트 Time: $time ms\n")
        }
    }

    @Test
    fun `runInCoroutine_테스트`() {
        runBlocking {
            println("runInCoroutine_테스트 before Active Thread = ${Thread.activeCount()}")
            val time= measureTimeMillis {
                val jobs = ArrayList<Job>()
                repeat(reps){
                    jobs += launch(Dispatchers.Default){
                        delay(1000L)
                    }
                }
                println("runInCoroutine_테스트 Active Thread = ${Thread.activeCount()}")
                jobs.forEach {
                    it.join()
                }
            }
            println("runInCoroutine_테스트 Time: $time ms\n")
        }
    }

}
