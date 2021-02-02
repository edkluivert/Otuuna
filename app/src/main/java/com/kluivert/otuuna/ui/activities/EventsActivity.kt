package com.kluivert.otuuna.ui.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.kluivert.otuuna.R


class EventsActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        setupActionBarWithNavController(findNavController(R.id.eventsFragment))


        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.eventsFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }




}