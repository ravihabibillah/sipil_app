package com.example.sipilapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.sipilapp.databinding.ActivityGuideBinding

class GuideActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGuideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBarTitle = "Petunjuk Penggunaan"
        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.guide_1))
        imageList.add(SlideModel(R.drawable.guide_2))
        imageList.add(SlideModel(R.drawable.guide_3))

        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)

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