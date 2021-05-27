package com.example.coroutinetest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coroutinetest.R
import com.example.coroutinetest.data.User
import com.example.coroutinetest.data.UserModel
import com.example.coroutinetest.manager.BaseUiObserver
import com.example.coroutinetest.manager.UiObserverManager
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private var viewModel: MainViewModel? = null
    private var mainUiObserver: BaseUiObserver? = null
    private var observer: Observer<List<User>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initUiObserver()
        initData()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
    }

    private fun initUiObserver() {
        /*mainUiObserver = object :BaseUiObserver{
            override fun getType(): BaseUiObserver.UiType {
                return BaseUiObserver.UiType.Main
            }

            override fun update(data: Bundle?) {
                val data = data?.getParcelableArrayList<User>(MainViewModel.BUNDLE_USER_DATA)
                Log.d(TAG, "data = $data")
            }
        }

        UiObserverManager.registerObserver(mainUiObserver!!)*/

        observer = Observer {
            Log.d(TAG, "data = $it")
        }

        if (observer != null)
            viewModel?.item?.observe(this@MainActivity, observer!!)
    }

    private fun initData(){
        viewModel?.fetchUser()
    }

    override fun onDestroy() {
        super.onDestroy()
        UiObserverManager.removeObserver(BaseUiObserver.UiType.Main)

        if (observer != null)
            viewModel?.item?.removeObserver(observer!!)
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
