package com.example.sipilapp.preferences

import android.content.Context
import com.example.sipilapp.data.DataReport

internal class ReportPreference(context: Context) {
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setReport(value: DataReport){
        val editor = preferences.edit()

        editor.putString(PROJECT, value.project)
        editor.putString(LOCATION, value.location)
        editor.putString(HOLE_ID, value.holeId)
        editor.putString(ENGINEER, value.engineer)
        editor.putFloat(EASTING, value.easting)
        editor.putFloat(NORTHING, value.northing)
        editor.putString(ELEVATION, value.elevation)
        editor.putString(DATE,value.date)
        editor.apply()
    }

    fun getReport(): DataReport{
        val model = DataReport()
        model.project = preferences.getString(PROJECT,"")
        model.location = preferences.getString(LOCATION,"")
        model.holeId = preferences.getString(HOLE_ID,"")
        model.engineer = preferences.getString(ENGINEER,"")
        model.easting = preferences.getFloat(EASTING, 0.0F)
        model.northing = preferences.getFloat(NORTHING, 0.0F)
        model.elevation = preferences.getString(ELEVATION,"")
        model.date = preferences.getString(DATE,"")

        return model
    }


    companion object {
        private const val PREFS_NAME = "report_pref"
        private const val PROJECT = "project"
        private const val LOCATION = "location"
        private const val HOLE_ID = "hole_id"
        private const val ENGINEER = "engineer"
        private const val EASTING = "easting"
        private const val NORTHING = "northing"
        private const val ELEVATION = "elevation"
        private const val DATE = "date"
    }
}