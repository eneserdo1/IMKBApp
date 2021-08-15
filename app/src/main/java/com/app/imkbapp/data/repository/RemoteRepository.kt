package com.app.imkbapp.data.repository

import com.app.imkbapp.data.remoteDataSource.RemoteDataSource
import com.app.imkbapp.model.Detail.StockDetailResponse
import com.app.imkbapp.model.Handshake.HandShakeResponse
import com.app.imkbapp.model.Resource
import com.app.imkbapp.model.Stocks.StocksResponse
import kotlinx.coroutines.flow.flow
import  kotlinx.coroutines.flow.Flow
import java.lang.Exception
import javax.inject.Inject

class RemoteRepository @Inject constructor(val remoteDataSource:RemoteDataSource){

    suspend fun fetchHandShake(bodyMap:Map<String,String>) :Flow<Resource<HandShakeResponse>>{
        return flow {
            emit(Resource.loading())
            try {
                emit(remoteDataSource.getHandShake(bodyMap))
            }catch (e:Exception){
                emit(Resource.error(e.message.toString(), null))
            }
        }
    }


    suspend fun fetchStockList(bodyMap:Map<String,String>,token:String) :Flow<Resource<StocksResponse>>{
        return flow {
            emit(Resource.loading())
            try {
                emit(remoteDataSource.getStocks(bodyMap,token))
            }catch (e:Exception){
                emit(Resource.error(e.message.toString(), null))
            }
        }
    }


    suspend fun fetchStockDetail(bodyMap:Map<String,String>,token:String) :Flow<Resource<StockDetailResponse>>{
        return flow {
            emit(Resource.loading())
            try {
                emit(remoteDataSource.getStockDetail(bodyMap,token))
            }catch (e:Exception){
                emit(Resource.error(e.message.toString(), null))
            }
        }
    }
}