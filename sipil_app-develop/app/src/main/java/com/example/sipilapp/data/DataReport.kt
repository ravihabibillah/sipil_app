package com.example.sipilapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataReport(
    var project: String? = "",
    var location: String? = "",
    var holeId: String? = "",
    var engineer: String? = "",
    var coordinate: String? = "",
    var elevation: String? = "",
    var date: String? = ""
): Parcelable
