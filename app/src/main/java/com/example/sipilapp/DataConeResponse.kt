package com.example.sipilapp

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class DataConeResponse(

	@field:SerializedName("Sheet1")
	val sheet1: List<Sheet1Item>
) : Parcelable

@Parcelize
data class Sheet1Item(

	@field:SerializedName("Cone_Resistance")
	val coneResistance: Int,

	@field:SerializedName("Total_Resistance")
	val totalResistance: Int
) : Parcelable
