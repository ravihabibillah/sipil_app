package com.example.sipilapp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.sipilapp.data.DataReport
import com.example.sipilapp.databinding.ActivityMainBinding
import com.example.sipilapp.preferences.ReportPreference
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var dataReport: DataReport
    private lateinit var binding: ActivityMainBinding
    private lateinit var mReportPreference: ReportPreference

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val mainViewModel by viewModels<MainViewModel>()

//        mainViewModel.listResistance.observe(this, {
//            setData(it)
//        })


        mReportPreference = ReportPreference(this)

        showPreferenceInform()

        val actionBarTitle = "Pengisian Data"
        supportActionBar?.title = actionBarTitle

        binding.btnChange.setOnClickListener(this)
        binding.btnCalculate.setOnClickListener(this)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        binding.edtDate.isFocusable = false

        binding.edtDate.setOnClickListener {
            val datePicker = DatePickerDialog(
                this@MainActivity,
                { _, year, monthOfYear, dayOfMonth ->
                    // tampilkan di edittext
                    binding.edtDate.setText("" + dayOfMonth + " - " + (monthOfYear+1) + " - " + year)
                }, year,month,day)

            datePicker.show()
        }

    }

    private fun showPreferenceInform() {
        dataReport = mReportPreference.getReport()
        binding.apply {
            edtProject.setText(dataReport.project)
            edtLocation.setText(dataReport.location)
            edtHoleId.setText(dataReport.holeId)
            edtEngineer.setText(dataReport.engineer)
            edtEasting.setText(dataReport.easting.toString())
            edtNorthing.setText(dataReport.northing.toString())
            edtElevation.setText(dataReport.elevation)
            edtDate.setText(dataReport.date)
        }

    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_change -> {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(URL)
                startActivity(intent)
            }
            R.id.btn_calculate -> {
                val project = binding.edtProject.text.toString().trim()
                val location = binding.edtLocation.text.toString().trim()
                val holeId = binding.edtHoleId.text.toString().trim()
                val engineer = binding.edtEngineer.text.toString().trim()
                val easting = binding.edtEasting.text.toString().trim()
                val northing = binding.edtNorthing.text.toString().trim()
                val elevation = binding.edtElevation.text.toString().trim()
                val date = binding.edtDate.text.toString().trim()

                if (project.isEmpty()) {
                    binding.edtProject.error = FIELD_REQUIRED
                    return
                }

                if (location.isEmpty()) {
                    binding.edtLocation.error = FIELD_REQUIRED
                    return
                }
                if (holeId.isEmpty()) {
                    binding.edtHoleId.error = FIELD_REQUIRED
                    return
                }
                if (engineer.isEmpty()) {
                    binding.edtEngineer.error = FIELD_REQUIRED
                    return
                }
                if (easting.isEmpty()) {
                    binding.edtEasting.error = FIELD_REQUIRED
                    return
                }
                if (northing.isEmpty()) {
                    binding.edtNorthing.error = FIELD_REQUIRED
                    return
                }
                if (elevation.isEmpty()) {
                    binding.edtElevation.error = FIELD_REQUIRED
                    return
                }
                if (date.isEmpty()) {
                    binding.edtDate.error = FIELD_REQUIRED
                    return
                }

                saveReport(project, location, holeId, engineer, easting, northing, elevation, date)

                // intent ke halaman tampilan data beserta tabel

                Toast.makeText(this, "Data Sedang Diproses", Toast.LENGTH_SHORT).show()
                val resultIntent = Intent(this@MainActivity, DataReportActivity::class.java)
                resultIntent.putExtra(EXTRA_RESULT, dataReport)
//                setResult(RESULT_CODE, resultIntent)
                startActivity(resultIntent)
            }
        }
    }

    private fun saveReport(
        project: String,
        location: String,
        holeId: String,
        engineer: String,
        easting: String,
        northing: String,
        elevation: String,
        date: String
    ) {
        val reportPreference = ReportPreference(this)

        dataReport.project = project
        dataReport.location = location
        dataReport.holeId = holeId
        dataReport.engineer = engineer
        dataReport.easting = easting.toDouble()
        dataReport.northing = northing.toDouble()
        dataReport.elevation = elevation
        dataReport.date = date

        reportPreference.setReport(dataReport)
//        Toast.makeText(this, "Data Tersimpan", Toast.LENGTH_SHORT).show()

    }

    companion object {
        const val URL =
            "https://docs.google.com/spreadsheets/d/1DSUQ_5KAFb8KZ0K3E9gZ4C_P2Ch7ErvYBSDwLidFXCM/edit?usp=sharing"

        const val EXTRA_RESULT = "extra_result"
//        const val RESULT_CODE = 101

        private const val FIELD_REQUIRED = "Field tidak boleh kosong"
    }
}