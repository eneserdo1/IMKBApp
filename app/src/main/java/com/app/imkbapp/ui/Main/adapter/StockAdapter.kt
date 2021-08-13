package com.app.imkbapp.ui.Main.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.imkbapp.R
import com.app.imkbapp.databinding.RecyclerviewItemStockBinding
import com.app.imkbapp.model.Stocks.StocksResponse
import com.app.imkbapp.util.EncryptionUtil
import com.app.imkbapp.util.getPref

class StockAdapter : RecyclerView.Adapter<StockAdapter.MyHolder>() {
    private lateinit var binding: RecyclerviewItemStockBinding
    var originalList : ArrayList<StocksResponse.Stock> = arrayListOf()
    var filteredList : ArrayList<StocksResponse.Stock> = arrayListOf()

    fun setList(newList:ArrayList<StocksResponse.Stock>){
        originalList = newList
        filteredList = newList
        notifyDataSetChanged()
    }

    inner class MyHolder(private val binding : RecyclerviewItemStockBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data:StocksResponse.Stock) = with(itemView){
            binding.apply {
                rowName.text = EncryptionUtil.decrypt(getPref(itemView.context).aesKey, getPref(itemView.context).aesIV,data.symbol)
                rowPrice.text = data.price
                rowDifference.text = data.difference
                rowVolume.text = "%.2f".format(data.volume)
                rowBuying.text = data.bid
                rowSelling.text = data.offer
                if (data.isUp == "true"){
                    binding.rowChange.setBackgroundResource(R.drawable.arrow_up_24)
                }else if (data.isDown == "true"){
                    binding.rowChange.setBackgroundResource(R.drawable.arrow_down_24)
                }


            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        binding = RecyclerviewItemStockBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(filteredList[position])
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }
}