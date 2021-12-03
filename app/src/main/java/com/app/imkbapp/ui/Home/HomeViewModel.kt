package com.app.imkbapp.ui.Home

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.imkbapp.data.repository.RemoteRepository
import com.app.imkbapp.model.Status
import com.app.imkbapp.model.Stocks.StocksResponse
import com.app.imkbapp.ui.Home.adapter.StockAdapter
import com.app.imkbapp.util.EncryptionUtil
import com.app.imkbapp.util.getPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repository: RemoteRepository,application: Application) : ViewModel() {

    val _mutableStocksResponse: MutableLiveData<StocksResponse> = MutableLiveData()
    val _mutableProgressbarState: MutableLiveData<Boolean> = MutableLiveData()



    fun getStockList(context: Context, period: String) {
        val bodyMap = HashMap<String, String>()
        bodyMap["period"] = period

        viewModelScope.launch {
            repository.fetchStockList(bodyMap, getPref(context).authToken).collect {
                when (it.status) {
                    Status.ERROR -> {
                        _mutableStocksResponse.value = null
                        _mutableProgressbarState.value = false
                    }
                    Status.LOADING -> {
                        _mutableProgressbarState.value = true
                    }
                    Status.SUCCESS -> {
                        try {
                            for (item in it.data!!.stocks!!) {
                                item.symbol = EncryptionUtil.decrypt(
                                    getPref(context).aesKey,
                                    getPref(context).aesIV,
                                    item.symbol
                                )
                            }
                            _mutableStocksResponse.value = it.data
                            _mutableProgressbarState.value = false
                        } catch (e: Exception) {
                            _mutableStocksResponse.value = null
                            _mutableProgressbarState.value = false
                            e.printStackTrace()
                        }

                    }
                }
            }
        }
    }


    fun filterList(term: String, adapter: StockAdapter) {
        if (term.length > 2) {
            println("term-  $term")
            val list = adapter.originalList.filter {
                it.symbol!!.contains(term, true)
            }
            adapter.stockList = list as ArrayList<StocksResponse.Stock>
            println("Fiiltered list-  $list")
            adapter.notifyDataSetChanged()
        } else {
            adapter.stockList = adapter.originalList
            adapter.notifyDataSetChanged()
        }
    }

}