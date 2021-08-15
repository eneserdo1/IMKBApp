package com.app.imkbapp.util

import androidx.lifecycle.MutableLiveData

class Constants {

    companion object{

        const val BASE_URL = "https://mobilechallenge.veripark.com/"

        var _mutableSelected = MutableLiveData<String>()
    }

}