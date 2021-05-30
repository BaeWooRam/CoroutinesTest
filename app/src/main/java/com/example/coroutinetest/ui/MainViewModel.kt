package com.example.coroutinetest.ui

import android.os.Bundle
import com.example.coroutinetest.data.UserModel
import com.example.coroutinetest.manager.BaseUiObserver
import com.example.coroutinetest.manager.UiObserverManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel {
    private val userModel = UserModel()

    /**
     * Job은 코루틴의 핸들입니다. lunch 또는 async로 만드는 각 코루틴은 코루틴을 고유하게 식별하고 수명주기를 관리하는 Job 인스턴스를 반환합니다.
     */
    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    /**
     * lunch : 새 코루틴을 시작하고 호출자에게 결과를 반환하지 않습니다. '실행 후 삭제'로 간주되는 모든 작업은 launch를 사용하여 시작할 수 있습니다.
     * async : 새 코루틴을 시작하고 await라는 정지 함수로 결과를 반환하도록 허용합니다.
     */
    fun fetchUser() {
        val job = scope.launch {
            val data = ArrayList(userModel.getUser())

            UiObserverManager.notifyUpdate(
                BaseUiObserver.UiType.Main,
                Bundle().apply {
                    putParcelableArrayList(BUNDLE_USER_DATA, data)
                })
        }

        if(job.isCancelled)
            job.cancel()
    }

    companion object {
        const val BUNDLE_USER_DATA = "bundle_user_data"
    }
}