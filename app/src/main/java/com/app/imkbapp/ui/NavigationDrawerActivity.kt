package com.app.imkbapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.app.imkbapp.R
import com.app.imkbapp.databinding.ActivityNavigationDrawerBinding
import com.app.imkbapp.ui.Base.BaseActivity
import com.app.imkbapp.util.Constants.Companion._mutableSelected
import com.app.imkbapp.util.EncryptionUtil
import com.app.imkbapp.util.getPref
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NavigationDrawerActivity : BaseActivity<ActivityNavigationDrawerBinding>(ActivityNavigationDrawerBinding::inflate),NavigationView.OnNavigationItemSelectedListener {
    var drawerLayout: DrawerLayout?=null
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun initViews() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_home), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.navigation_drawer, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_ascend -> {
                val period = EncryptionUtil.encrypt(
                    getPref(this).aesKey,
                    getPref(this).aesIV,
                    "increasing"
                )
                _mutableSelected.postValue(period)
            }
            R.id.nav_descend -> {
                val period = EncryptionUtil.encrypt(
                    getPref(this).aesKey,
                    getPref(this).aesIV,
                    "decreasing"
                )
                _mutableSelected.postValue(period)
            }
            R.id.nav_stocks -> {
                val period = EncryptionUtil.encrypt(
                    getPref(this).aesKey,
                    getPref(this).aesIV,
                    "all"
                )
                _mutableSelected.postValue(period)
            }
            R.id.nav_volume30 -> {
                val period = EncryptionUtil.encrypt(
                    getPref(this).aesKey,
                    getPref(this).aesIV,
                    "volume30"
                )
                _mutableSelected.postValue(period)
            }
            R.id.nav_volume50 -> {
                val period = EncryptionUtil.encrypt(
                    getPref(this).aesKey,
                    getPref(this).aesIV,
                    "volume50"
                )
                _mutableSelected.postValue(period)
            }
            R.id.nav_volume100 -> {
                val period = EncryptionUtil.encrypt(
                    getPref(this).aesKey,
                    getPref(this).aesIV,
                    "volume100"
                )
                _mutableSelected.postValue(period)
            }
        }
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            drawerLayout!!.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right)
        }
    }
}