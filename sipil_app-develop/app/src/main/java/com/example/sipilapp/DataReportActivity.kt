package com.example.sipilapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.viewModels
import com.example.sipilapp.data.DataReport
import com.example.sipilapp.databinding.ActivityDataReportBinding
import com.example.sipilapp.preferences.ReportPreference
import com.example.sipilapp.viewmodel.MainViewModel
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import com.example.sipilapp.data.DataCalc
import java.io.Serializable


class DataReportActivity : AppCompatActivity() {

    private lateinit var mReportPreference: ReportPreference

    private lateinit var dataReport: DataReport
    private lateinit var listDataCalc: List<DataCalc>

    private lateinit var binding: ActivityDataReportBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Data Report"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mReportPreference = ReportPreference(this)

        val mainViewModel by viewModels<MainViewModel>()

        mainViewModel.listResistance.observe(this, {
            showTable(it)
        })

        mainViewModel.toastText.observe(this, {
            it.getContentIfNotHandled()?.let { toastText ->
                Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
            }
        })

        clickableButtonToGraph(false)

        showExistingPreference()

        binding.btnToGraph.setOnClickListener {
            val intent = Intent(this@DataReportActivity, GraphActivity::class.java)
            intent.putExtra(EXTRA_RESULT, listDataCalc as Serializable)
            startActivity(intent)
        }

    }

    private fun clickableButtonToGraph(click: Boolean) {
        binding.btnToGraph.isEnabled = click
        binding.btnToGraph.isClickable = click
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

    private fun showTable(listResistance: List<RecordsItem>) {
        val tableMain = binding.tableMain

//        showLoading(false)

        val rowTitle = TableRow(this)
//        val tvparams = TableRow.LayoutParams(
//            TableRow.LayoutParams.MATCH_PARENT,
//            TableRow.LayoutParams.WRAP_CONTENT
//        )
//        tvparams.setMargins(16,16,16,16)


        val textDepth = TextView(this)
        setTextViewStyleInTable(
            rowTitle,
            textDepth,
            (getString(R.string.depth) + getString(R.string.in_meter)),
        )

        val textCone = TextView(this)
        setTextViewStyleInTable(
            rowTitle,
            textCone,
            (getString(R.string.cone_resistance) + getString(R.string.in_kilos_per_centi))
        )


        val textTotalPenetration = TextView(this)
        setTextViewStyleInTable(
            rowTitle,
            textTotalPenetration,
            getString(R.string.total_resistance) + getString(R.string.in_kilos_per_centi)
        )


        val textLocalFriction = TextView(this)
        setTextViewStyleInTable(
            rowTitle,
            textLocalFriction,
            getString(R.string.local_friction) + getString(R.string.in_kilos_per_centi)
        )

        val textTotalCumulativeFriction = TextView(this)
        setTextViewStyleInTable(
            rowTitle,
            textTotalCumulativeFriction,
            getString(R.string.total_cumulative_friction) + getString(R.string.in_kilos_per_centi)
        )

        val textFrictionRatio = TextView(this)
        setTextViewStyleInTable(
            rowTitle,
            textFrictionRatio,
            getString(R.string.friction_ratio) + getString(R.string.in_percent)
        )

        val textSoilType = TextView(this)
        setTextViewStyleInTable(
            rowTitle,
            textSoilType,
            getString(R.string.soil_type)
        )

        val textSoilTypeHandbor = TextView(this)
        setTextViewStyleInTable(
            rowTitle,
            textSoilTypeHandbor,
            getString(R.string.soil_type_handbor)
        )

        rowTitle.setBackgroundResource(R.drawable.border)
//        showLoading(true)
        tableMain.addView(rowTitle)
        addDataToTable(listResistance, tableMain)

    }


    private fun addDataToTable(listResistance: List<RecordsItem>, tableMain: TableLayout) {
//        showLoading(true)
        var depth = 0.00

        var index = 0
        val tempList = mutableListOf<DataCalc>()

        for (data in listResistance) {
//            showLoading(true)
            val dataCalc = DataCalc()
            val convertDepth = (String.format("%.2f", depth)).toFloat()
            val tbrow = TableRow(this)


            // Set Depth
            val tvDepth = TextView(this)
            setTextViewStyleInTable(tbrow, tvDepth, convertDepth.toString())
            dataCalc.depth = convertDepth

            // Set Cone Resistance
            val tvCone = TextView(this)
            setTextViewStyleInTable(tbrow, tvCone, data.coneResistance.toDouble().toString())

            // Set Total Resistance
            val tvTotalResist = TextView(this)
            setTextViewStyleInTable(
                tbrow,
                tvTotalResist,
                data.totalResistance.toDouble().toString()
            )


            // Set Local Friction
            val tvLocalFriction = TextView(this)
            val localFriction = calculateLocalFriction(data.coneResistance, data.totalResistance)
            setTextViewStyleInTable(tbrow, tvLocalFriction, localFriction.toString())
            dataCalc.localFriction = localFriction

            // Set Total Cumulative
            val tvTotalCumulativeFriction = TextView(this)

            val totalCumulative: Float = if (tempList.isEmpty()) {
                0.00F
            } else {
                calculateTotalCumulative(localFriction, tempList[index - 1].totalCumulative)
            }


            setTextViewStyleInTable(
                tbrow,
                tvTotalCumulativeFriction,
                totalCumulative.toString()
            )
            dataCalc.totalCumulative = totalCumulative

            // Set Friction Ration
            val tvFrictionRatio = TextView(this)
            val frictionRatio: Float = if (data.coneResistance == 0) {
                0.00F
            } else {
                calculateFrictionRatio(data.coneResistance, localFriction)
            }
            setTextViewStyleInTable(tbrow, tvFrictionRatio, frictionRatio.toString())
            dataCalc.frictionRatio = frictionRatio

            // Set Soil Type
            val tvSoilType = TextView(this)
            val soilType = checkSoilType(frictionRatio)
            setTextViewStyleInTable(tbrow, tvSoilType, soilType)

            // Set Soil Type Handbor
            val tvSoilTypeHandbor = TextView(this)
            val soilTypeHandbor = data.soilTypeHandbor
            setTextViewStyleInTable(tbrow, tvSoilTypeHandbor, soilTypeHandbor)

            tempList.add(dataCalc)
            tableMain.addView(tbrow)
            depth -= 0.2
            index += 1
        }
//        Log.d(TAG, "addDataToTable: $tempList")
        listDataCalc = tempList
//        showLoading(false)

        clickableButtonToGraph(true)

    }

    private fun checkSoilType(frictionRatio: Float): String {
        var soilType = "-"
        if (frictionRatio > 0.0) {
            when {
                frictionRatio < 0.5 -> {
                    soilType = "Rock, shells & loose gravel"
                }
                frictionRatio < 2 -> {
                    soilType = "Sand / gravel"
                }
                frictionRatio < 5 -> {
                    soilType = "Clay-sand mixture & silt"
                }
                frictionRatio > 5 -> {
                    soilType = "Clay"
                }
            }
        }

        return soilType
    }


    private fun calculateLocalFriction(coneResistance: Int, totalResistance: Int): Float {
        val aProyeksi = 10.00
        val aPiston = 10.00
        val aSelimut = 106.5

        return String.format(
            "%.2f",
            ((totalResistance.toDouble() * aPiston) - (coneResistance.toDouble() * aProyeksi)) / aSelimut
        ).toFloat()
    }

    private fun calculateTotalCumulative(localFriction: Float, prevTotalCumulative: Float): Float {
        return prevTotalCumulative + (localFriction * 20)
    }

    private fun calculateFrictionRatio(coneResistance: Int, localFriction: Float): Float {

        return String.format(
            "%.2f",
            ((localFriction / coneResistance) * 100)
        ).toFloat()
    }

    private fun setTextViewStyleInTable(tableRow: TableRow, textView: TextView, text: String) {
        textView.text = text
        textView.gravity = Gravity.CENTER
        textView.setPadding(16, 16, 16, 16)
        textView.setBackgroundResource(R.drawable.border)
        tableRow.addView(textView)
    }

    private fun showExistingPreference() {
        dataReport = mReportPreference.getReport()
        populateView(dataReport)
    }

    private fun populateView(dataReport: DataReport) {
        binding.apply {
            tvProject.text =
                if (dataReport.project.toString().isEmpty()) "Tidak Ada" else dataReport.project
            tvLocation.text =
                if (dataReport.location.toString().isEmpty()) "Tidak Ada" else dataReport.location
            tvHoleId.text =
                if (dataReport.holeId.toString().isEmpty()) "Tidak Ada" else dataReport.holeId
            tvEngineer.text =
                if (dataReport.engineer.toString().isEmpty()) "Tidak Ada" else dataReport.engineer
            tvCoordinate.text = if (dataReport.coordinate.toString()
                    .isEmpty()
            ) "Tidak Ada" else dataReport.coordinate
            tvElevation.text =
                if (dataReport.elevation.toString().isEmpty()) "Tidak Ada" else dataReport.elevation
            tvDate.text = if (dataReport.date.toString().isEmpty()) "Tidak Ada" else dataReport.date
        }
    }

//    private fun showLoading(isLoading: Boolean) {
//        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//    }


    companion object {
        const val EXTRA_RESULT = "extra_result"
    }
}