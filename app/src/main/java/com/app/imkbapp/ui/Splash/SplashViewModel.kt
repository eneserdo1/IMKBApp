package com.app.imkbapp.ui.Splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.imkbapp.data.repository.RemoteRepository
import com.app.imkbapp.model.Handshake.HandShakeResponse
import com.app.imkbapp.model.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(val repository: RemoteRepository) : ViewModel() {

    val _mutableHandShakeResponse : MutableLiveData<HandShakeResponse> = MutableLiveData()
    val _mutableProgressbarState : MutableLiveData<Boolean> = MutableLiveData()


    fun getHandShake(id:String,systemVersion : String,platformName:String,deviceModel:String,manifacturer:String){
        val bodyMap = HashMap<String,String>()
        bodyMap["deviceId"] = id
        bodyMap["systemVersion"] = systemVersion
        bodyMap["platformName"] = platformName
        bodyMap["deviceModel"] = deviceModel
        bodyMap["manifacturer"] = manifacturer

        _mutableProgressbarState.value = true
        viewModelScope.launch {
            repository.fetchHandShake(bodyMap).collect {
                when(it.status){
                    Status.ERROR->{
                        _mutableHandShakeResponse.value = null
                        _mutableProgressbarState.value = false
                    }
                    Status.LOADING->{
                        _mutableProgressbarState.value = true
                    }
                    Status.SUCCESS->{
                        _mutableHandShakeResponse.value = it.data
                        _mutableProgressbarState.value = false
                    }
                }
            }
        }
    }
}