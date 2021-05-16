package com.example.coroutinetest.manager

import android.os.Bundle

interface BaseUiObserver {
    enum class UiType{
        Main
    }

    fun getType():UiType
    fun update(data: Bundle?)
}