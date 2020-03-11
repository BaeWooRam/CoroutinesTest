package com.example.coroutinetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    val service =
        ServiceBuilder.getService("https://jsonplaceholder.typicode.com/", UserService::class.java)

    var _userList: List<User>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.launch(Dispatchers.IO) {
            val job = service.getUsers()

//                        delegateUser(job).flowOn() Context수정

            //필터 & Collect
            delegateUser(job)
                .filter {
                    return@filter it.id == 1
                }.collect {
                    Log.i(javaClass.simpleName, it.toString())
                }

//            Map & Collect
            delegateUser(job).map {
                it.email
            }.collect{
                Log.i(javaClass.simpleName, it.toString())
            }

//            필터 & toList
            _userList = delegateUser(job).filter {
                return@filter true
            }.toList()

            Log.i("필터 & toList", "size : ${_userList?.size}")

            //Take & Reduce
            val user = delegateUser(job)
                .reduce { accumulator, value ->
                    accumulator.id = accumulator.id + value.id
                    Log.i("Take & Reduce", "acc : ${accumulator.toString()}")
                    Log.i("Take & Reduce", "value : ${value.toString()}")
                    return@reduce accumulator
                }

            Log.i(javaClass.simpleName, user.toString())

            // Zip
            delegateUser(job)
                .zip(flowOf(10, 20, 30, 40, 50)) { user, num ->
                user.id = num
                    return@zip user
                }.collect {
                Log.i("Zip USER", it.toString())
                }

        }

    }

    private fun delegateUser(users: List<User>): Flow<User> = flow {
        Log.i(javaClass.simpleName, "isEmpty : ${users.isEmpty()}")

        for (user in users) {
            emit(user)
        }
    }
}
