package com.example.coroutinetest.data.repository

import com.example.coroutinetest.data.Post
import com.example.coroutinetest.data.User
import com.example.coroutinetest.data.api.PostService
import com.example.coroutinetest.data.api.UserService
import com.example.coroutinetest.data.remote.PostDataSourceImp
import kotlinx.coroutines.flow.*
import kotlinx.serialization.json.Json
import java.io.File

class PostRepositoryImp(
    private val postService: PostService
) : PostRepository {
    private val postDataSource = PostDataSourceImp(postService)

    override suspend fun getPost(): Flow<List<Post>> {
        return postDataSource.execute()
    }
}