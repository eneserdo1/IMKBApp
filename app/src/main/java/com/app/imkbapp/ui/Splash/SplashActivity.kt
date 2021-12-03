package com.app.imkbapp.ui.Splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.imkbapp.R
import com.app.imkbapp.databinding.ActivityMainBinding
import com.app.imkbapp.databinding.ActivitySplashBinding
import com.app.imkbapp.ui.Base.BaseActivity
import com.app.imkbapp.ui.NavigationDrawerActivity
import com.app.imkbapp.util.PhoneUtil
import com.app.imkbapp.util.getPref
import com.app.imkbapp.util.hideProgressDialog
import com.app.imkbapp.util.showProgressDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    private val viewModel: SplashViewModel by viewModels<SplashViewModel>()


    override fun onStart() {
        super.onStart()
        getRequestResources()
        observers()
        buttonsListener()
    }

    private fun getRequestResources() {
        val id = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        val manifacturer = PhoneUtil.getManifacturer()
        val deviceModel = PhoneUtil.getDeviceName()
        val platfomName = "Android"
        val systemVersion = android.os.Build.VERSION.RELEASE

        viewModel.getHandShake(id,systemVersion,platfomName,deviceModel,manifacturer)
    }

    private fun buttonsListener() {
        binding.fetchDataButton.setOnClickListener {
            val intent = Intent(this, NavigationDrawerActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left)
        }
    }

    private fun observers() {
        viewModel._mutableHandShakeResponse.observe(this, Observer { response->
            if (response != null){
                getPref(this).aesIV = response.aesIV
                getPref(this).aesKey = response.aesKey
                getPref(this).authToken = response.authorization
            }else{
                Toast.makeText(this,getString(R.string.bir_hata_olusut), Toast.LENGTH_LONG).show()
            }
        })


        viewModel._mutableProgressbarState.observe(this, Observer { response->
            response?.let {
                if (it){
                    this.showProgressDialog()
                }else{
                    hideProgressDialog()
                }
            }
        })
    }

    override fun onStop() {
        super.onStop()
        hideProgressDialog()
    }

}