package com.example.coroutinetest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coroutinetest.R
import com.example.coroutinetest.data.User
import com.example.coroutinetest.data.UserModel
import com.example.coroutinetest.manager.BaseUiObserver
import com.example.coroutinetest.manager.UiObserverManager
import kotlinx.coroutines.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private var viewModel: MainViewModel? = null
    private var mainUiObserver: BaseUiObserver? = null
    private var observer: Observer<List<User>>? = null
    private var tvInfo:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        initViewModel()
        initUiObserver()
        initData()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, MainViewModel.MainViewModelFactory())[MainViewModel::class.java]
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

            if(tvInfo == null)
                tvInfo = findViewById<TextView>(R.id.tvInfo)

            tvInfo?.text = it.toString()
        }

        if (observer != null)
            viewModel?.item?.observe(this@MainActivity, observer!!)
    }

    private fun initData(){
        viewModel?.fetchUser()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
        UiObserverManager.removeObserver(BaseUiObserver.UiType.Main)

        if (observer != null)
            viewModel?.item?.removeObserver(observer!!)
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
