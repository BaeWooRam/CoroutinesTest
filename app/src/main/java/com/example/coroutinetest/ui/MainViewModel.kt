package com.example.coroutinetest.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import com.example.coroutinetest.data.PostModel
import com.example.coroutinetest.data.User
import com.example.coroutinetest.data.UserModel
import com.example.coroutinetest.manager.BaseUiObserver
import com.example.coroutinetest.manager.UiObserverManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

/**
 * ViewModel (or AndroidViewModel -> Application Context가 필요하면 이걸로 사용)
 * : 수명 주기를 고려하여 UI 관련 데이터를 저장하고 관리하도록 설계되었습니다. ViewModel 클래스를 사용하여 화면 회전과 같이 구성을 변경할 때도 데이터를 유지할 수 있습니다.
 *
 * 1) 구현
 * 아키텍처 구성요소는 UI의 데이터 준비를 담당하는 UI 컨트롤러에 ViewModel 도우미 클래스를 제공합니다. ViewModel 객체는 구성이 변경되는 동안 자동으로 보관되므로
 * 이러한 객체가 보유한 데이터는 다음 활동 또는 프래그먼트 인스턴스에서 즉시 사용할 수 있습니다.
 *
 * 2) 수명주기
 * ViewModel 객체의 범위는 ViewModel을 가져올때 ViewModelProvider에 전달되는 Lifecycle로 지정됩니다.
 * ViewModel은 범위가 지정된 Lifecycle이 영구적으로 경과될 때까지, 즉 활동에서는 활동이 끝날 때까지 그리고 프로그먼트에서는 프로그먼트가 분리될 때까지 메모리에 남아 있습니다.
 */
class MainViewModel(private val index: Int) : ViewModel() {

    /**
     * LiveData
     * : 식별가능한 데이터 홀더 클래스입니다. 일반 식별 가능한 클래스와 달리 LiveData는 수명 주기를 인식합니다.
     * 즉, 활동, 프래그먼트, 서비스 등 다른 앱 구성요소의 수명 주기를 고려합니다. 수명 주기 인식을 통해 LiveData는 활동 수명 주기 상태에 있는 앱 구성 요소 관찰자만 업데이트합니다.
     *
     * Observer 클래스로 표현되는 관찰자이 수명 주기가 STARTED 또는 RESUMED 상태이면 LiveData는 관찰자를 활성 상태로 간주합니다.
     * LiveData는 활성 관찰자에게만 업데이트 정보를 알립니다. 비활성 관찰자는 변경사항에 관한 알림을 받지 못합니다.
     *
     * LifecycleOwner 인터페이스를 구현하는 객체와 페어링된 관찰자를 등록할 수 있습니다. 그리하여 Lifecycle 객체가 DESTORYED로 변경될 때 관찰자를 삭제할 수 있습니다.
     *
     * -개체 사용
     * 1) 특정 유형의 데이터를 보유할 LiveData 인스턴스를 생성합니다. 이 작업은 일반적으로 ViewModel에서 이루어집니다.
     * 2) onChaged 메서드를 정의하는 Observer 객체를 생성합니다. 이 메서드는 LiveData 객체가 보유한 데이터 변경 시 발생하는 작업을 제어합니다. 일반적으로 활동이나 프래그먼트 같은 UI 컨트롤러에서 만듭니다.
     * 3) observe 메서드를 사용해 LiveData 객체에 Observer 객체를 연결합니다. observe 메서드는 LifecyclerOwner 객체를 사용합니다.
     *
     * 항상 활성화 상태로 간주하기 위해서는 observeForever(Observer) 메서드를 사용합니다. 지울 때는 removeObserver(Observer) 메서드를 사용합니다.
     */
    val item: MutableLiveData<List<User>> = MutableLiveData<List<User>>()
    var filterItem1: MutableLiveData<String> = MutableLiveData<String>()
    var filterItem2: MutableLiveData<String> = MutableLiveData<String>()
    val mediatorLiveData: MediatorLiveData<User> = MediatorLiveData()
    private val userModel = UserModel()
    private val postModel = PostModel()

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
            Log.d(TAG, "index = $index, data = $data")
            item.postValue(data)
        }
    }

    /**
     * 소유자 활동이 완료되면 프레임 워크는 리소스를 정리할 수 있도록 onCleared를 호출합니다.
     */
    override fun onCleared() {
        super.onCleared()
    }

    /**
     * 해당 ViewModel에 종속성을 주입시키는 방법이다.
     * 이 경우에는 특정 객체나 값을 주입시키기 위한 생성자라고 생각하면 될듯하다.
     */
    class MainViewModelFactory() : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(1) as T
        }
    }


    fun getEvent() = callbackFlow<Unit> {
        userModel.getUser()

        //이벤트를 흐름에 보냅니다. 소비자는 새로운 이벤트를 받게 됩니다.
        offer(Unit)

        //awaitClose 내부의 콜백은 흐름이 닫히거나 취소되었습니다.
        awaitClose()

        //Firebase를 초기화 할 수 없는 경우 데이터 스트림을 닫습니다. 흐름 소비자가 수집을 중지하고 코루틴이 다시 시작됩니다.
        close()
        postModel.getPosts()
    }

    companion object {

        const val TAG = "MainViewModel"
        const val BUNDLE_USER_DATA = "bundle_user_data"
    }
}