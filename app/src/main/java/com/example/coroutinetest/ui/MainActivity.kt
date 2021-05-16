package com.example.coroutinetest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coroutinetest.R
import com.example.coroutinetest.data.User
import com.example.coroutinetest.data.UserModel
import com.example.coroutinetest.manager.BaseUiObserver
import com.example.coroutinetest.manager.UiObserverManager
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val viewModel: MainViewModel by lazy {
        MainViewModel()
    }

    private var mainUiObserver:BaseUiObserver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUiObserver()
        initData()
    }

    private fun initUiObserver(){
        mainUiObserver = object :BaseUiObserver{
            override fun getType(): BaseUiObserver.UiType {
                return BaseUiObserver.UiType.Main
            }

            override fun update(data: Bundle?) {
                val data = data?.getParcelableArrayList<User>(MainViewModel.BUNDLE_USER_DATA)
                Log.d(TAG, "data = $data")
            }
        }

        UiObserverManager.registerObserver(mainUiObserver!!)
    }

    private fun initData(){
        viewModel.fetchUser()
    }

    override fun onDestroy() {
        super.onDestroy()
        UiObserverManager.removeObserver(BaseUiObserver.UiType.Main)
    }

    companion object{
        const val TAG = "MainActivity"
    }
}
