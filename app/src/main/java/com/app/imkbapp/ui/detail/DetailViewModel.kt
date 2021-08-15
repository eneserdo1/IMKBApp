package com.app.imkbapp.ui.detail

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.imkbapp.data.repository.RemoteRepository
import com.app.imkbapp.model.Detail.StockDetailResponse
import com.app.imkbapp.model.Status
import com.app.imkbapp.util.getPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(val repository: RemoteRepository): ViewModel() {

    val _mutableStocksResponse: MutableLiveData<StockDetailResponse> = MutableLiveData()
    val _mutableProgressbarState: MutableLiveData<Boolean> = MutableLiveData()



    fun getStockDetail(context: Context, id: String) {
        val bodyMap = HashMap<String, String>()
        bodyMap["id"] = id

        viewModelScope.launch {
            repository.fetchStockDetail(bodyMap, getPref(context).authToken).collect {
                when (it.status) {
                    Status.ERROR -> {
                        _mutableStocksResponse.value = null
                        _mutableProgressbarState.value = false
                    }
                    Status.LOADING -> {
                        _mutableProgressbarState.value = true
                    }
                    Status.SUCCESS -> {
                        _mutableStocksResponse.value = it.data
                        _mutableProgressbarState.value = false
                    }
                }
            }
        }
    }

}