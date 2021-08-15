package com.app.imkbapp.model.Detail

import com.app.imkbapp.model.Stocks.StocksResponse
import com.google.gson.annotations.SerializedName

data class StockDetailResponse(
    @SerializedName("isDown") val isDown: String,
    @SerializedName("isUp") val isUp: String,
    @SerializedName("bid") val bid: String,
    @SerializedName("change") val change: String,
    @SerializedName("count") val count: String,
    @SerializedName("difference") val difference: String,
    @SerializedName("offer") val offer: String,
    @SerializedName("highest") val highest: String,
    @SerializedName("lowest") val lowest: Double,
    @SerializedName("maximum") var maximum: String,
    @SerializedName("minimum") val minimum: String,
    @SerializedName("price") val price: String,
    @SerializedName("volume") val volume: Double,
    @SerializedName("symbol") var symbol: String,
    @SerializedName("graphicData") var graphicData: List<GraphicData>,
    @SerializedName("status") val status: Status

    ) {
    data class GraphicData(
        @SerializedName("day") val day: Int? = null,
        @SerializedName("value") val value: Double? = null,
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
