package com.example.coroutinetest.data.api

import com.example.coroutinetest.data.Post
import retrofit2.http.GET

interface PostService {
    @GET("posts")
    suspend fun getPosts():List<Post>

}