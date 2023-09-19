package com.example.coroutinetest.data.remote

import com.example.coroutinetest.data.Post
import com.example.coroutinetest.data.api.PostService
import kotlinx.coroutines.flow.*

class PostDataSourceImp(
    private val postService: PostService
) : PostDataSource {
    override suspend fun execute(): Flow<List<Post>> = flow {
        val result = postService.getPosts()
        emit(result)
    }
}