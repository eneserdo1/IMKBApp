package com.app.imkbapp.util

import android.content.Context
import com.app.imkbapp.data.local.PreferencesHelper

fun getPref(context: Context):PreferencesHelper{
    return PreferencesHelper.getInstane(context)
}