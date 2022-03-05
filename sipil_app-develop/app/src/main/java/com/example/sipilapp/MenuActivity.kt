package com.example.sipilapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.sipilapp.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ibHitung.setOnClickListener(this)
        binding.ibProfil.setOnClickListener(this)
        binding.ibReferensi.setOnClickListener(this)
        binding.ibPetunjuk.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.ib_hitung -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.ib_profil -> {
                val intent = Intent(this, ProfilActivity::class.java)
                startActivity(intent)
            }
            R.id.ib_referensi -> {
                val intent = Intent(this, ReferenceActivity::class.java)
                startActivity(intent)
            }
            R.id.ib_petunjuk -> {
                val intent = Intent(this, GuideActivity::class.java)
                startActivity(intent)
            }
        }
    }
}