package com.example.sipilapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem

class ProfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)


        val actionBarTitle = getString(R.string.profil)
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