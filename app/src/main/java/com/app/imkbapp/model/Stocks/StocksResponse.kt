package com.app.imkbapp.model.Stocks

import com.app.imkbapp.model.Handshake.HandShakeResponse
import com.google.gson.annotations.SerializedName

data class StocksResponse(

        @SerializedName("stocks") val stocks:List<Stock>?=null,
        @SerializedName("status") val status:Status
){
    data class Stock(
            @SerializedName("id") val id:String,
            @SerializedName("isDown") val isDown:String,
            @SerializedName("isUp") val isUp:String,
            @SerializedName("bid") val bid:String,
            @SerializedName("difference") val difference:String,
            @SerializedName("offer") val offer:String,
            @SerializedName("price") val price:String,
            @SerializedName("volume") val volume:Double,
            @SerializedName("symbol") val symbol:String
    )

    data class Status(
            @SerializedName("isSuccess") val isSuccess : String,
            @SerializedName("error") val error : Error,
    )

    data class Error(
            @SerializedName("code") val code: String,
            @SerializedName("message") val message:String
    )

}
