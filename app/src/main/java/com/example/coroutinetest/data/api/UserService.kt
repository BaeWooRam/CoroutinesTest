package com.example.coroutinetest.data.api

import com.example.coroutinetest.data.User
import retrofit2.http.GET

interface UserService {
    @GET("users")
    suspend fun getUsers():List<User>

}