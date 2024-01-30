package com.example.coroutinetest.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutinetest.R
import com.example.coroutinetest.manager.EventObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Timer
import java.util.TimerTask
import kotlin.random.Random

class HotStreamActivity : AppCompatActivity(R.layout.activity_main) {
    private val workList = mutableListOf<Thread>()
    private val timer = Timer()

    private fun test() {
        for (index in 0..4) {
            val target = Thread {
                runBlocking(Dispatchers.Default) {
                    EventObserver.eventSubscriber.collect { event ->
                        Log.d(
                            "SampleActivity12341234",
                            "threadId = $index, id = ${event.id}, data = ${event.data}"
                        )
                    }
                }
            }

            workList.add(target)
            target.start()
        }

        Log.d("SampleActivity12341234", "workList size = ${workList.size}")

        val task = object : TimerTask() {
            override fun run() {
                CoroutineScope(Dispatchers.Default).launch {
                    val id = Random.nextInt(0, 999)
                    Log.d("SampleActivity12341234", "send event id = $id")
                    EventObserver.eventPublisher.tryEmit(
                        EventObserver.Event(
                            id.toString(),
                            Bundle()
                        )
                    )
                }
            }
        }

        timer.schedule(task, 0, 10000)
    }
}