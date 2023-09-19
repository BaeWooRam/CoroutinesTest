package com.example.coroutinetest.data.repository

import android.content.res.AssetManager
import com.example.coroutinetest.data.User
import com.example.coroutinetest.data.api.UserService
import com.example.coroutinetest.data.remote.UserDataSourceImp
import com.example.coroutinetest.data.remote.UserDetailDataSourceImp
import kotlinx.coroutines.flow.*

class UserRepositoryImp(
    private val assetManager: AssetManager,
    private val userService: UserService
) : UserRepository {
    private val userDataSource = UserDataSourceImp(userService)
    private val userDetailDataSource = UserDetailDataSourceImp(assetManager)

    override suspend fun getUserFlow(): Flow<User> {
        return userDataSource.execute().combine(userDetailDataSource.execute()) { user, userDetails ->
            val userDetail = userDetails.find { userDetail -> userDetail.id == user.id }

            if(userDetail != null){
                user.userDetail = userDetail
            }

            return@combine user
        }
    }

    override suspend fun getUserId(id: Int): Flow<User?> {
        return getUserFlow().filter { it.id == id }
    }
}