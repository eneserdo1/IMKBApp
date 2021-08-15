package com.app.imkbapp.util

import android.app.ProgressDialog
import android.content.Context
import com.app.imkbapp.R
import com.app.imkbapp.data.local.PreferencesHelper

var mProgressDialog: ProgressDialog? = null


fun getPref(context: Context):PreferencesHelper{
    return PreferencesHelper.getInstane(context)
}

fun Context.showProgressDialog() {
    if (mProgressDialog == null) {
        mProgressDialog = ProgressDialog(this)
        mProgressDialog!!.setMessage(this.getString(R.string.loading))
        mProgressDialog!!.isIndeterminate = true
    }
    mProgressDialog!!.show()
}


fun hideProgressDialog() {
    if (mProgressDialog != null && mProgressDialog!!.isShowing) {
        mProgressDialog!!.dismiss()
    }
}