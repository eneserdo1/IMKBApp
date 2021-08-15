package com.app.imkbapp.data.remoteDataSource

import com.app.imkbapp.data.ApiService
import com.app.imkbapp.model.Detail.StockDetailResponse
import com.app.imkbapp.model.Handshake.HandShakeResponse
import com.app.imkbapp.model.Resource
import com.app.imkbapp.model.Stocks.StocksResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val apiService:ApiService){

    suspend fun getHandShake(bodyMap:Map<String,String>): Resource<HandShakeResponse> {
        val response = apiService.getHandShake(bodyMap)
        return Resource.success(response)
    }

    suspend fun getStocks(bodyMap:Map<String,String>,token:String): Resource<StocksResponse> {
        val response = apiService.getStocks(bodyMap,token)
        return Resource.success(response)
    }

    suspend fun getStockDetail(bodyMap:Map<String,String>,token:String): Resource<StockDetailResponse> {
        val response = apiService.getStocksDetail(bodyMap,token)
        return Resource.success(response)
    }
}