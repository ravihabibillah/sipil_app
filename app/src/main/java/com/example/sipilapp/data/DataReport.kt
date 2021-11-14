package com.example.sipilapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataReport(
    var project: String? = "",
    var location: String? = "",
    var holeId: String? = "",
    var engineer: String? = "",
    var easting: Double = 0.0,
    var northing: Double = 0.0,
    var elevation: String? = "",
    var date: String? = ""
): Parcelable
