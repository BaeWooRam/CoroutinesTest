package com.example.coroutinetest.data.remote

import android.content.res.AssetManager
import com.example.coroutinetest.data.UserDetail
import kotlinx.coroutines.flow.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File

class UserDetailDataSourceImp(
    private val assetManager:AssetManager,
    private val json: Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }
) : UserDetailDataSource {

    override suspend fun execute(): Flow<List<UserDetail>> = flow {
        val inputStream = assetManager.open("sessions.json")
        val result = json.decodeFromStream<List<UserDetail>>(inputStream)
        emit(result)
    }
}