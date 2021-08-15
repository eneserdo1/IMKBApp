package com.app.imkbapp.ui.detail

import android.content.Context
import android.widget.TextView
import com.app.imkbapp.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils

class MyMarkerView(context : Context, layoutResource:Int ) : MarkerView(context , layoutResource) {

    private var tvContent : TextView? = findViewById(R.id.tvContent)

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        if (e is CandleEntry) {
            val ce = e;
            tvContent!!.setText("" + Utils.formatNumber(ce!!.x ,0, true));
        } else {
            tvContent!!.setText("" + Utils.formatNumber(e!!.getY(), 0, true));
        }
        super.refreshContent(e, highlight);

    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(getWidth() / 2)).toFloat(), (-getHeight()).toFloat())
    }
}
