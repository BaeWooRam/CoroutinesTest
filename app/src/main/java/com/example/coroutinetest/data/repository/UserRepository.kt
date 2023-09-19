package com.example.coroutinetest.data.repository

import com.example.coroutinetest.data.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserFlow():Flow<User>
    suspend fun getUserId(id:Int):Flow<User?>
}