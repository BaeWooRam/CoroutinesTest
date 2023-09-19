package com.example.coroutinetest.data.remote

import com.example.coroutinetest.data.UserDetail
import kotlinx.coroutines.flow.Flow

interface UserDetailDataSource {
    suspend fun execute(): Flow<List<UserDetail>>
}