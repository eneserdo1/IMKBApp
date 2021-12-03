package com.app.imkbapp.ui.Detail

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.app.imkbapp.R
import com.app.imkbapp.databinding.FragmentDetailBinding
import com.app.imkbapp.model.Detail.StockDetailResponse
import com.app.imkbapp.ui.Base.BaseFragment
import com.app.imkbapp.util.EncryptionUtil
import com.app.imkbapp.util.getPref
import com.app.imkbapp.util.hideProgressDialog
import com.app.imkbapp.util.showProgressDialog
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.EntryXComparator
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val TAG = "DetailFragment "
    private val viewModel: DetailViewModel by viewModels<DetailViewModel>()
    private var id : String?=null
    private var graphData: ArrayList<StockDetailResponse.GraphicData> = arrayListOf()
    private var chartPeriod = ArrayList<String>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id = requireArguments().getString("id")
        id?.let {
            val encryptedId = EncryptionUtil.encrypt(getPref(requireContext()).aesKey, getPref(requireContext()).aesIV,it)
            viewModel.getStockDetail(requireContext(),encryptedId)
        }
        observers()
    }

    private fun observers() {
        viewModel._mutableStocksResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response != null) {
                initUI(response)
            } else {
                Toast.makeText(requireContext(), getString(R.string.bir_hata_olusut), Toast.LENGTH_LONG).show()
            }
        })

        viewModel._mutableProgressbarState.observe(viewLifecycleOwner, Observer { response->
            response?.let {
                if (it){
                    requireContext().showProgressDialog()
                }else{
                    hideProgressDialog()
                }
            }
        })
    }

    private fun initUI(response: StockDetailResponse) {
        binding.apply {
            symbolTxt.text = EncryptionUtil.decrypt(getPref(requireContext()).aesKey, getPref(requireContext()).aesIV,response.symbol)
            priceTxt.text = response.price
            differenceTxt.text = response.difference
            volumeTxt.text = response.volume.toString()
            buyingTxt.text = response.bid
            sellingTxt.text = response.offer
            lowestTxt.text = response.lowest.toString()
            highestTxt.text = response.highest
            countTxt.text = response.count
            maxTxt.text = response.maximum
            minTxt.text = response.minimum

            if (response.isUp == "true"){
                binding.changeImg.setImageDrawable(requireContext().getDrawable(R.drawable.arrow_up_24))
            }else if (response.isDown == "true"){
                binding.changeImg.setImageDrawable(requireContext().getDrawable(R.drawable.arrow_down_24))
            }else{
                binding.changeImg.setImageDrawable(requireContext().getDrawable(R.drawable.arrow_right_alt_24))
            }

        }
        graphData = response.graphicData as ArrayList<StockDetailResponse.GraphicData>
        setGraph()

    }

    private fun setGraph() {
        val values = ArrayList<Entry>()
        values.clear()

        chartPeriod.clear()
        binding.lineChart.setDragOffsetX(20f)
        binding.lineChart.setDragOffsetY(10f)
        binding.lineChart.fitScreen()
        for (item in graphData) {
            values.add(Entry(item.day!!.toFloat(), item.value!!.toFloat()))
        }
        Collections.sort(values, EntryXComparator())
        val set1: LineDataSet
        if (!values.isNullOrEmpty()) {
            var drawable: Drawable? = null
            var color: String = ""
            drawable = ContextCompat.getDrawable(requireContext(), R.drawable.fade_blue)
            color = "#8D8E8D"
            set1 = LineDataSet(values, "Stocks")
            set1.mode = LineDataSet.Mode.CUBIC_BEZIER
            //line kıvrım/çizgi kıvrıklığı
            set1.cubicIntensity = 0.05f
            //chart altındaki alan coloru
            set1.setDrawFilled(true)
            //line altının transparenti
            set1.fillAlpha = 100
            set1.lineWidth = 3f
            set1.color = Color.parseColor(color)
            set1.setDrawCircles(false)
            set1.circleRadius = 1f
            set1.setCircleColor(Color.parseColor(color))
            set1.setDrawCircleHole(false)
            set1.fillDrawable = drawable
            //chartın boyuna y çizgisi
            set1.setDrawHorizontalHighlightIndicator(false)
            set1.setFillFormatter { dataSet, dataProvider ->
                binding.lineChart.axisLeft.axisMinimum
            }
            val dataSets: ArrayList<ILineDataSet> = ArrayList()
            dataSets.add(set1) // add the data sets
            // create a data object with the data sets
            val data = LineData(dataSets)
            // set data
            data.setValueTextSize(9f)
            data.setDrawValues(false)
            data.setValueTextColor(Color.WHITE)

            //Zoom
            binding.lineChart.setPinchZoom(false)
            binding.lineChart.isAutoScaleMinMaxEnabled = true//?
            binding.lineChart.isDoubleTapToZoomEnabled = false

            binding.lineChart.setDrawGridBackground(false)
            //chart içindeki desc
            binding.lineChart.description.isEnabled = false
            //sağdaki yazılar disable
            binding.lineChart.axisRight.isEnabled = false
            binding.lineChart.xAxis.isEnabled = true
            binding.lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            //lineChart.getAxisLeft().setAxisMaximum(35);
            binding.lineChart.xAxis.textColor = Color.GRAY
            binding.lineChart.axisLeft.textColor = Color.GRAY
            binding.lineChart.xAxis.setDrawGridLines(false)
            //Chart name enable, below chart
            binding.lineChart.legend.isEnabled = false
            binding.lineChart.marker = MyMarkerView(
                requireContext(),
                R.layout.custom_marker_view
            )
            binding.lineChart.setOnChartValueSelectedListener(object :
                OnChartValueSelectedListener {
                override fun onValueSelected(e: Entry?, h: Highlight?) {
                }

                override fun onNothingSelected() {
                }
            })
            binding.lineChart.data = data
        }
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG,"OnStop")
        hideProgressDialog()
    }

}