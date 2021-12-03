package com.app.imkbapp.ui.Home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.imkbapp.R

import com.app.imkbapp.databinding.FragmentHomeBinding
import com.app.imkbapp.model.Stocks.StocksResponse
import com.app.imkbapp.ui.Base.BaseFragment
import com.app.imkbapp.ui.Home.adapter.ItemClickListener
import com.app.imkbapp.ui.Home.adapter.StockAdapter
import com.app.imkbapp.util.Constants.Companion._mutableSelected
import com.app.imkbapp.util.EncryptionUtil
import com.app.imkbapp.util.getPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val TAG = "HomeFragment "
    private val viewModel: HomeViewModel by viewModels<HomeViewModel>()
    private lateinit var stockAdapter: StockAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerview()
        Observers()
        buttonsListener()
        searchviewListener()

        val period = EncryptionUtil.encrypt(getPref(requireContext()).aesKey, getPref(requireContext()).aesIV,"all")
        viewModel.getStockList(requireContext(), period)
    }

    private fun searchviewListener() {
        binding.searchView.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                    if (p0.isNullOrEmpty()){
                        binding.searchButton.visibility = View.VISIBLE
                        binding.clearButton.visibility = View.GONE

                    }else{
                        viewModel.filterList(p0.toString(),stockAdapter)
                        binding.searchButton.visibility = View.GONE
                        binding.clearButton.visibility = View.VISIBLE
                    }
            }
        })
    }

    private fun buttonsListener() {
        binding.clearButton.setOnClickListener {
            viewModel.filterList(" ",stockAdapter)
            binding.searchView.setText("")
            binding.searchView.clearFocus()
        }

    }

    private fun initRecyclerview() {
        stockAdapter = StockAdapter(object : ItemClickListener {
            override fun selectedStock(id: String) {
                val bundle = Bundle()
                bundle.putString("id",id)
                Navigation.findNavController(requireView()).navigate(R.id.action_nav_home_to_nav_detail,bundle)
            }
        })
        binding.stockRecyclerview.apply {
            adapter = stockAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


    private fun Observers() {
        viewModel._mutableStocksResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response != null) {
                stockAdapter.setList(response.stocks as ArrayList<StocksResponse.Stock>)
            } else {
                Toast.makeText(requireContext(), getString(R.string.bir_hata_olusut), Toast.LENGTH_LONG).show()
            }
        })

        viewModel._mutableProgressbarState.observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                if (it) {
                    binding.progressbar.visibility = View.VISIBLE
                } else {
                    binding.progressbar.visibility = View.GONE
                }
            }
        })

        _mutableSelected.observe(viewLifecycleOwner, Observer { response->
            if (response != null){
                viewModel.getStockList(requireContext(), response)
            }else{
                Toast.makeText(requireContext(), getString(R.string.bir_hata_olusut), Toast.LENGTH_LONG).show()
            }
        })

    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG,"OnStop")
        binding.progressbar.visibility = View.GONE
    }
}