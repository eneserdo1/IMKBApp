package com.app.imkbapp.data.local

import android.content.Context
import android.content.SharedPreferences
import java.util.*
import javax.inject.Inject
import android.preference.PreferenceManager.getDefaultSharedPreferences

class PreferencesHelper @Inject constructor(context: Context) {

    private val mPreferences : SharedPreferences = getDefaultSharedPreferences(context)
    private val mEditör : SharedPreferences.Editor = mPreferences.edit()

    companion object{
        private var instance : PreferencesHelper?=null

        const val VALUE_AES_KEY : String = "val.aes.key"
        const val VALUE_AES_IV: String = "val.aes.ıv"
        const val VALUE_AUTHORIZATION: String = "val.auth"


        fun getInstane(context: Context):PreferencesHelper{
            if (instance == null)
                instance = PreferencesHelper(context)

            return instance as PreferencesHelper
        }
    }

    var authToken : String
        get() = mPreferences.getString(VALUE_AUTHORIZATION,"")!!
        set(value){
            mEditör.putString(VALUE_AUTHORIZATION,value)
            mEditör.commit()
        }

    var aesIV : String
        get() = mPreferences.getString(VALUE_AES_IV,"")!!
        set(value){
            mEditör.putString(VALUE_AES_IV,value)
            mEditör.commit()
        }

    var aesKey : String
        get() = mPreferences.getString(VALUE_AES_KEY,"")!!
        set(value){
            mEditör.putString(VALUE_AES_KEY,value)
            mEditör.commit()
        }


    fun clearSharedPreferenes(){
        mEditör.clear()
        mEditör.commit()
    }
}