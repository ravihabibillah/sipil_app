package com.example.sipilapp

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.example.sipilapp.data.DataCalc
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class GraphActivity : AppCompatActivity() {
    private lateinit var series1: LineGraphSeries<DataPoint>
    private lateinit var series2: LineGraphSeries<DataPoint>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        val listDataCalc: List<DataCalc> =
            intent.getSerializableExtra(EXTRA_RESULT) as List<DataCalc>


        supportActionBar?.title = "Grafik Data Report"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val graphView1 = findViewById<GraphView>(R.id.first_graph)
        val graphView2 = findViewById<GraphView>(R.id.second_graph)

        series1 = LineGraphSeries<DataPoint>()
        series2 = LineGraphSeries<DataPoint>()

        var x = 0.0
        var y = 0.0
        Log.d(TAG, "onCreate: $listDataCalc")
        for (data in listDataCalc) {
            y = data.depth.toDouble()
            x = data.totalCumulative.toDouble()
            series1.appendData(DataPoint(x, y), true, 1000)
        }


        graphView1.apply {
            title = "Relation Between Depth and TCF"
            viewport.isScrollable = true
            addSeries(series1)
            gridLabelRenderer.horizontalAxisTitle = "TCF"
            gridLabelRenderer.verticalAxisTitle = "Depth"
        }

        for (data in listDataCalc) {
            x = data.depth.toDouble() * -1
            y = data.frictionRatio.toDouble()
            series2.appendData(DataPoint(x, y), true, 1000)
        }


        graphView2.apply {
            title = "Relation Between Depth and FR"
            viewport.isScrollable = true
            addSeries(series2)
            gridLabelRenderer.horizontalAxisTitle = "Depth"
            gridLabelRenderer.verticalAxisTitle = "FR"
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> true
        }
    }

    companion object {
        const val EXTRA_RESULT = "extra_result"
    }
}