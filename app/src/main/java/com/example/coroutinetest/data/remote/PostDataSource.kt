package com.example.coroutinetest.data.remote

import com.example.coroutinetest.data.Post
import kotlinx.coroutines.flow.Flow

interface PostDataSource {
    suspend fun execute():Flow<List<Post>>
}