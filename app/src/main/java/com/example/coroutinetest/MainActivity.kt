package com.example.coroutinetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    val service =
        ServiceBuilder.getService("https://jsonplaceholder.typicode.com/", UserService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.launch(Dispatchers.IO) {
            val job = service.getUsers()

            delegateUser(job).collect{
                Log.i(javaClass.simpleName, it.toString())
            }
        }

    }

    private fun delegateUser(users: List<User>):Flow<User> = flow{
        Log.i(javaClass.simpleName, "isEmpty : ${users.isEmpty()}")

        for (user in users) {
            emit(user)
        }
    }
}
