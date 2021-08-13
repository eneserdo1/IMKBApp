package com.app.imkbapp.ui.Splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.imkbapp.databinding.ActivityMainBinding
import com.app.imkbapp.databinding.ActivitySplashBinding
import com.app.imkbapp.ui.Main.MainActivity
import com.app.imkbapp.ui.Main.MainViewModel
import com.app.imkbapp.util.PhoneUtil
import com.app.imkbapp.util.getPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels<SplashViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val manifacturer = PhoneUtil.getManifacturer()
        val deviceModel = PhoneUtil.getDeviceName()
        val platfomName = "Android"
        val systemVersion = android.os.Build.VERSION.RELEASE

        viewModel.getHandShake(Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID),systemVersion,platfomName,deviceModel,manifacturer)
        observers()
        buttonsListener()
    }

    private fun buttonsListener() {
        binding.fetchDataButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observers() {
        viewModel._mutableHandShakeResponse.observe(this, Observer { response->
            if (response != null){
                getPref(this).aesIV = response.aesIV
                getPref(this).aesKey = response.aesKey
                getPref(this).authToken = response.authorization
            }else{
                Toast.makeText(this,"Bir hata oluştu", Toast.LENGTH_LONG).show()
            }
        })
    }

}