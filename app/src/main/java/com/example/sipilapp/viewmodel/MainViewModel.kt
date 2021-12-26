package com.example.sipilapp.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sipilapp.DataConeResponse
import com.example.sipilapp.Event
import com.example.sipilapp.Sheet1Item
import com.example.sipilapp.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _listResistance = MutableLiveData<List<Sheet1Item>>()
    val listResistance: LiveData<List<Sheet1Item>> = _listResistance

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

//    private val _dataReport = MutableLiveData<DataReport>()
//    val dataReport: LiveData<DataReport> = _dataReport

//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading

    init {
        findData()
    }

    private fun findData() {
//        _isLoading.value = true

        // Request API
        val client = ApiConfig.getApiService().getListData(DATA_ID, SHEET)
        client.enqueue(object : Callback<DataConeResponse> {
            override fun onResponse(
                call: Call<DataConeResponse>,
                response: Response<DataConeResponse>
            ) {
//                _isLoading.value = false

                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _listResistance.value = response.body()?.sheet1
                    _toastText.value = Event("Data berhasil diproses!")

                } else {
                    Log.e(TAG, "onFailure1: ${response.message()}")

                    _toastText.value = Event(response.message())
                }
            }

            override fun onFailure(call: Call<DataConeResponse>, t: Throwable) {
//                _isLoading.value = false
                Log.e(TAG, "onFailure2: ${t.message}")
                _toastText.value = Event("Data gagal diproses..\nSilahkan periksa koneksi internet")
//                Toast.makeText(, "Data Sedang Diproses..\nMohon tunggu sebentar", Toast.LENGTH_LONG).show()

            }

        })
    }


    companion object {
        private const val DATA_ID = "1DSUQ_5KAFb8KZ0K3E9gZ4C_P2Ch7ErvYBSDwLidFXCM"
        private const val SHEET = "Sheet1"
        private const val TAG = "MainViewModel"
    }
}