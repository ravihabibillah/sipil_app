package com.example.sipilapp.api

import com.example.sipilapp.DataConeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec")
    fun getListData(
        @Query("id") id: String,
        @Query("sheet") sheet: String
    ): Call<DataConeResponse>

}