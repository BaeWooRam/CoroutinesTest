package com.example.coroutinetest.data.remote

import com.example.coroutinetest.data.User
import com.example.coroutinetest.data.api.UserService
import kotlinx.coroutines.flow.*

class UserDataSourceImp(
    private val userService: UserService
) : UserDataSource {

    override suspend fun execute(): Flow<User> = flow {
        val users = userService.getUsers()
        for (user in users){
            emit(user)
        }
    }
}