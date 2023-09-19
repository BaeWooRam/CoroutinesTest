package com.example.coroutinetest.data.repository

import com.example.coroutinetest.data.Post
import com.example.coroutinetest.data.User
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun getPost():Flow<List<Post>>
}