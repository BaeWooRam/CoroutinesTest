package com.example.coroutinetest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.*
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
    private var btLoadInfo:Button? = null
    private var btRandomTest:Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        initViewModel()
        initView()
        initUiObserver()
        initData()
    }

    private fun initView(){
        tvInfo = findViewById<TextView>(R.id.tvInfo)
        btLoadInfo = findViewById<Button>(R.id.btLoadInfo).apply { setOnClickListener { initData() } }
        btRandomTest = findViewById<Button>(R.id.btRandomTest).apply { setOnClickListener {
            when((0..1).random()){
                0 ->  {
                    viewModel!!.filterItem1 = MutableLiveData()
                    viewModel!!.filterItem1!!.postValue((0..10).random().toString())
                }
                1 ->  {
                    viewModel!!.filterItem2 = MutableLiveData()
                    viewModel!!.filterItem2!!.postValue((0..10).random().toString())
                }
            }
        } }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, MainViewModel.MainViewModelFactory())[MainViewModel::class.java]

        viewModel!!.mediatorLiveData.observe(this, Observer{
            Log.d(TAG, "mediatorLiveData user = $it")
        })

        viewModel!!.mediatorLiveData!!.addSource(viewModel!!.filterItem1){
            Log.d(TAG, "FilterItem1 onChange = $it")
            viewModel!!.mediatorLiveData.postValue(User(1, "filter1 temp", "filter1 temp2", "filter1 temp3"))
        }

        viewModel!!.mediatorLiveData!!.addSource(viewModel!!.filterItem2){
            Log.d(TAG, "FilterItem2 onChange = $it")
            viewModel!!.mediatorLiveData.postValue(User(2, "filter2 temp", "filter2 temp2", "filter2 temp3"))
        }
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
