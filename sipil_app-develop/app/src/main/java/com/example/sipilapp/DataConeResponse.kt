package com.example.sipilapp

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class DataConeResponse(

	@field:SerializedName("records")
	val sheet1: List<RecordsItem>
) : Parcelable

@Parcelize
data class RecordsItem(

	@field:SerializedName("Cone_Resistance")
	val coneResistance: Int,

	@field:SerializedName("Total_Resistance")
	val totalResistance: Int
) : Parcelable
