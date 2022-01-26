package com.example.sipilapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataCalc(
    var depth: Float = 0.0F,
    var localFriction: Float = 0.00F,
    var totalCumulative: Float = 0.00F,
    var frictionRatio: Float = 0.00F
) : Parcelable
