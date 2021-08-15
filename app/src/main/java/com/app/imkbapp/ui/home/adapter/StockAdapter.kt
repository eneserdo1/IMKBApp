package com.app.imkbapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.app.imkbapp.R
import com.app.imkbapp.databinding.RecyclerviewItemStockBinding
import com.app.imkbapp.model.Stocks.StocksResponse

class StockAdapter(val itemSelectedListener: ItemClickListener) : RecyclerView.Adapter<StockAdapter.MyHolder>() {
    private lateinit var binding: RecyclerviewItemStockBinding
    var originalList : ArrayList<StocksResponse.Stock> = arrayListOf()
    var stockList : ArrayList<StocksResponse.Stock> = arrayListOf()

    fun setList(newList:ArrayList<StocksResponse.Stock>){
        originalList = newList
        stockList = newList
        notifyDataSetChanged()
    }


    inner class MyHolder(private val binding : RecyclerviewItemStockBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data:StocksResponse.Stock) = with(itemView){
            binding.apply {
                rowName.text = data.symbol
                rowPrice.text = data.price
                rowDifference.text = data.difference
                rowVolume.text = "%.2f".format(data.volume)
                rowBuying.text = data.bid
                rowSelling.text = data.offer

                if (data.isUp == "true"){
                    binding.rowChange.setImageDrawable(context.getDrawable(R.drawable.arrow_up_24))
                }else if (data.isDown == "true"){
                    binding.rowChange.setImageDrawable(context.getDrawable(R.drawable.arrow_down_24))
                }else{
                    binding.rowChange.setImageDrawable(context.getDrawable(R.drawable.arrow_right_alt_24))
                }

                itemView.setOnClickListener {
                    itemSelectedListener.selectedStock(data.id)
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        binding = RecyclerviewItemStockBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(stockList[position])
    }

    override fun getItemCount(): Int {
        return stockList.size
    }



}