package com.kluivert.otuuna.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.kluivert.otuuna.R
import com.kluivert.otuuna.databinding.ActivityMainBinding
import com.kluivert.otuuna.ui.auth.AuthActivity
import com.kluivert.otuuna.viewModels.AuthViewModel
import es.dmoral.toasty.Toasty


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
   private lateinit var firebaseAuth: FirebaseAuth

   private lateinit var authViewModel : AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setTheme(R.style.Theme_SplashScreen)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()




        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        val navController = findNavController(R.id.fragmentContainer)
        bottomNav.setupWithNavController(navController)
    }



    fun checkUser(){
        if(firebaseAuth.currentUser == null){
            goHome()
        }
    }

    fun goHome(){
        Intent(this,AuthActivity::class.java).also {
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(it)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        }
    }



    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}