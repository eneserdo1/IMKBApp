package com.app.imkbapp.data

import com.app.imkbapp.model.Handshake.HandShakeResponse
import com.app.imkbapp.model.Stocks.StocksResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("api/handshake/start")
    suspend fun getHandShake(@Body bodyMap:Map<String,String>): HandShakeResponse


    @Headers("Content-Type: application/json")
    @POST("api/stocks/list")
    suspend fun getStocks(@Body bodyMap:Map<String,String>,@Header("X-VP-Authorization") token:String): StocksResponse
}