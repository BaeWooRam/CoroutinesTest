package com.example.coroutinetest

import com.google.gson.annotations.SerializedName

data class User(
    var id: Int,
    var name: String,
    var email:String,
    @SerializedName("username") var userName: String) {


}