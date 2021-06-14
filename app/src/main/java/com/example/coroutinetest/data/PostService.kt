package com.example.coroutinetest.data

import com.example.coroutinetest.data.User
import retrofit2.http.GET

interface PostService {
    @GET("posts")
    suspend fun getPosts():List<Posts>

}