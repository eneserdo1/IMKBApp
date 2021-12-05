package com.app.imkbapp.data.remoteDataSource

import com.app.imkbapp.data.ApiService
import com.app.imkbapp.model.Detail.StockDetailResponse
import com.app.imkbapp.model.Handshake.HandShakeResponse
import com.app.imkbapp.model.Resource
import com.app.imkbapp.model.Stocks.StocksResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val apiService:ApiService):BaseDataSource(){

    suspend fun getHandShake(bodyMap:Map<String,String>): Resource<HandShakeResponse> {
        return getResponse(request = {apiService.getHandShake(bodyMap)},defaultErrorMessage = "Error get handshake response")
    }

    suspend fun getStocks(bodyMap:Map<String,String>,token:String): Resource<StocksResponse> {
        return getResponse(request = {apiService.getStocks(bodyMap,token)},defaultErrorMessage = "Error get stocks")
    }

    suspend fun getStockDetail(bodyMap:Map<String,String>,token:String): Resource<StockDetailResponse> {
        return getResponse(request = {apiService.getStocksDetail(bodyMap,token)},defaultErrorMessage = "Error get stock detail")
    }
}