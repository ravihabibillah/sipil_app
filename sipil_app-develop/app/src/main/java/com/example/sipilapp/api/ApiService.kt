package com.example.sipilapp.api

import com.example.sipilapp.DataConeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("macros/s/AKfycbxGJ44bOEjBm_nMIyaon6eqc1n9yBGfSA2wKxN6Zq0IGn61GAVHrzzOn47BsdPMaBA/exec")
    fun getListData(
        @Query("id") id: String,
        @Query("sheet") sheet: String
    ): Call<DataConeResponse>

}