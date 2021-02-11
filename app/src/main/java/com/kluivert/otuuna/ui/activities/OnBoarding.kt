package com.kluivert.otuuna.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.kluivert.otuuna.R
import com.kluivert.otuuna.adapters.onboarding.OnBoardingAdapter
import com.kluivert.otuuna.data.Slides
import com.kluivert.otuuna.databinding.ActivityOnBoardingBinding

class OnBoarding : AppCompatActivity() {

    private lateinit var binding : ActivityOnBoardingBinding
    private lateinit var adapter : OnBoardingAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        var list = listOf(
            Slides(R.drawable.favicon,R.drawable.otuunacamera,"Spot an Otuuna photographer",
            "Otuuna have professional photographers who are available at any events."
                ),
            Slides(R.drawable.favicon,R.drawable.otuunaphoto,"Take Pictures","Engage an Otuuna photographer and take as much pics as you like"),
            Slides(R.drawable.favicon,R.drawable.otuunapay,"Pay for the pictures you love","Choose from a gallery of high quality and retouched photos" +
                    "at an affordable price")
        )
        adapter = OnBoardingAdapter(list)

        binding.otuunaviewpager.adapter = adapter
           TabLayoutMediator(binding.tabLayout,binding.otuunaviewpager){tab, position ->

           }.attach()

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

    }

    override fun onResume() {
        super.onResume()
        val prefs = "slide"
        val sharedPreferences = getSharedPreferences("slideprefs", Context.MODE_PRIVATE)
        if(!sharedPreferences.getBoolean(prefs,false)){
            val editor = sharedPreferences.edit()
            editor.putBoolean(prefs,true)
            editor.apply()
        }else {
            Intent(this@OnBoarding, AuthActivity::class.java).also {
                startActivity(it)
            }
        }

    }
}