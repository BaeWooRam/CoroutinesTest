package com.example.coroutinetest.data.remote

import com.example.coroutinetest.data.User
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    suspend fun execute(): Flow<User>
}