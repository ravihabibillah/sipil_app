package com.example.sipilapp

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.example.sipilapp.data.DataCalc
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class GraphActivity : AppCompatActivity() {
    //        private lateinit var series1: LineGraphSeries<DataPoint>
//    private lateinit var series2: LineGraphSeries<DataPoint>
    private val entries1: ArrayList<Entry> = ArrayList()
    private val entries2: ArrayList<Entry> = ArrayList()
    private lateinit var lineChart1: LineChart
    private lateinit var lineChart2: LineChart
    private lateinit var listDataCalc: List<DataCalc>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        listDataCalc =
            intent.getSerializableExtra(EXTRA_RESULT) as List<DataCalc>


        supportActionBar?.title = "Grafik Data Report"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        lineChart1 = findViewById(R.id.first_graph)
        lineChart2 = findViewById(R.id.second_graph)
//        val graphView2 = findViewById<GraphView>(R.id.second_graph)

//        series1 = LineGraphSeries<DataPoint>()
//        series2 = LineGraphSeries<DataPoint>()


        // Set Data untuk LineChart 1
        var x = 0.0F
        var y = 0.0F
        for (data in listDataCalc) {
            y = data.depth
            x = data.totalCumulative
//            series1.appendData(DataPoint(x, y), true, 1000)
            entries1.add(Entry(x, y))
        }
        val lineDataSet1 = LineDataSet(entries1, "TCF dan Depth")
        lineChart1.data = LineData(lineDataSet1);

        // Set LineChart 1
        initLineChart(lineDataSet1, lineChart1)

//        // Set Data untuk LineChart 2
        for (data in listDataCalc) {
            x = data.depth * -1
            y = data.frictionRatio
//            series1.appendData(DataPoint(x, y), true, 1000)
            entries2.add(Entry(x, y))
        }
        val lineDataSet2 = LineDataSet(entries2, "FR dan Depth")
        lineChart2.data = LineData(lineDataSet2);
        // Set LineChart 2
        initLineChart(lineDataSet2, lineChart2)

    }

    private fun initLineChart(lineDataSet: LineDataSet, lineChart: LineChart) {
        // Dataset Setting
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        lineDataSet.setDrawCircles(false)
        lineDataSet.setDrawValues(false)
        lineDataSet.lineWidth = 3f

        // LineChart Setting
        val xAxis: XAxis = lineChart.xAxis
        lineChart.setDrawBorders(true)

        //remove right y-axis
        lineChart.axisRight.isEnabled = false

        //remove legend
        lineChart.legend.isEnabled = true


        //remove description label
        lineChart.description.isEnabled = false
//        lineChart1.description.textSize = 10F
//        lineChart1.description.text = "Grafik hubungan antara Depth dan TCF"


        //add animation
        lineChart.animateX(1000, Easing.EaseInSine)

        // to draw label on xAxis
        if (lineChart == lineChart2) {
            xAxis.valueFormatter = MyAxisFormatter()
            xAxis.labelRotationAngle = -90f

        }
        xAxis.position = XAxis.XAxisPosition.BOTTOM
    }

    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            return if (index < listDataCalc.size) {
                listDataCalc[index].depth.toString()
            } else {
                ""
            }
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