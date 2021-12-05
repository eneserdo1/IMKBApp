package com.app.imkbapp.data.repository

import com.app.imkbapp.data.remoteDataSource.RemoteDataSource
import com.app.imkbapp.model.Detail.StockDetailResponse
import com.app.imkbapp.model.Handshake.HandShakeResponse
import com.app.imkbapp.model.Resource
import com.app.imkbapp.model.Stocks.StocksResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import  kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteRepository @Inject constructor(val remoteDataSource:RemoteDataSource){

    suspend fun fetchHandShake(bodyMap:Map<String,String>) :Flow<Resource<HandShakeResponse>>{
        return flow {
            emit(remoteDataSource.getHandShake(bodyMap))
        }.flowOn(Dispatchers.IO)
    }


    suspend fun fetchStockList(bodyMap:Map<String,String>,token:String) :Flow<Resource<StocksResponse>>{
        return flow {
            emit(remoteDataSource.getStocks(bodyMap,token))
        }.flowOn(Dispatchers.IO)
    }


    suspend fun fetchStockDetail(bodyMap:Map<String,String>,token:String) :Flow<Resource<StockDetailResponse>>{
        return flow {
            emit(remoteDataSource.getStockDetail(bodyMap,token))
        }.flowOn(Dispatchers.IO)
    }
}