package com.app.imkbapp.ui.Main

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.imkbapp.databinding.ActivityMainBinding
import com.app.imkbapp.model.Stocks.StocksResponse
import com.app.imkbapp.ui.Main.adapter.StockAdapter
import com.app.imkbapp.util.EncryptionUtil
import com.app.imkbapp.util.getPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels<MainViewModel>()
    private lateinit var stockAdapter: StockAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerview()
        val period = EncryptionUtil.encrypt(getPref(this).aesKey, getPref(this).aesIV,"all")
        viewModel.getStockList(this, period)
        Observers()
        buttonsListener()

    }

    private fun buttonsListener() {
        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                p0?.let {
                    if (p0.length >2){
                        viewModel.filterList(p0.toString(),stockAdapter)
                    }
                }
                return false
            }
        })
    }

    private fun initRecyclerview() {
        stockAdapter = StockAdapter()
        binding.stockRecyclerview.apply {
            adapter = stockAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun Observers() {
        viewModel._mutableStocksResponse.observe(this, Observer { response ->
            if (response != null) {
                Toast.makeText(this, "İşlem başarılı", Toast.LENGTH_LONG).show()
                stockAdapter.setList(response.stocks as ArrayList<StocksResponse.Stock>)
            } else {
                Toast.makeText(this, "Bir hata oluştu", Toast.LENGTH_LONG).show()
            }
        })

        viewModel._mutableProgressbarState.observe(this, Observer {response->
            response?.let {
                if (it){
                    binding.progressbar.visibility = View.VISIBLE
                }else{
                    binding.progressbar.visibility = View.GONE
                }
            }
        })
    }


}