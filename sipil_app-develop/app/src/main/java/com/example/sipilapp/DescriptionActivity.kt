package com.example.sipilapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.sipilapp.databinding.ActivityDescriptionBinding
import com.example.sipilapp.databinding.ActivityMainBinding

class DescriptionActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUserGuide.setOnClickListener (this)
        binding.btnNext.setOnClickListener (this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_user_guide -> {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(URL)
                startActivity(intent)
            }
            R.id.btn_next -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    companion object {
        const val URL =
            "https://docs.google.com/document/d/1XTHay7KxjbBoQzxJa-AY9CeLNpHW30QM/edit?usp=sharing&ouid=107881316907219857104&rtpof=true&sd=true"
    }

}