package com.example.coroutinetest.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class PostModel() {
    private val postService =
        ServiceBuilder.getService("https://jsonplaceholder.typicode.com/", PostService::class.java)
    private val dispatcher = Dispatchers.IO

    /**
     * withContext 내에 Dispatchers를 호출하여 IO 스레드 풀에서 실행되는 블록을 만듭니다.
     * 블록 안에 넣은 코드는 항상 withContext에 지정된 Dispatchers를 통해 실행됩니다.(withContext 자체가 정지함수)
     *
     * 주의사항 : withContext를 사용하는 모든 함수가 기본적으로 안전한지, 즉 기본 스레드에서 함수를 호출할 수 있는지 확인하는 것이 좋다.
     *
     */
    suspend fun getPosts() = withContext(dispatcher){
        postService.getPosts()
    }


    /*
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
     */


    /**
     * Flow
     */
    private fun delegateUser(users: List<User>): Flow<User> = flow {
        Log.i(javaClass.simpleName, "isEmpty : ${users.isEmpty()}")

        for (user in users) {
            emit(user)
        }
    }
}