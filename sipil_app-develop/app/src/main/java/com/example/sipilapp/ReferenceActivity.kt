package com.example.sipilapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem

class ReferenceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reference)

        val actionBarTitle = getString(R.string.referensi)
        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> true
        }
    }
}