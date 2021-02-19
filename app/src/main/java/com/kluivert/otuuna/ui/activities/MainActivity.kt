package com.kluivert.otuuna.ui.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.kluivert.otuuna.R
import com.kluivert.otuuna.databinding.ActivityMainBinding
import com.kluivert.otuuna.viewModels.AuthViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
   private lateinit var firebaseAuth: FirebaseAuth

   private lateinit var authViewModel : AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseAuth = FirebaseAuth.getInstance()
       checkUser()
       /* window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }*/

       binding.hamburger.setOnClickListener {
           binding.drawerLayout.openDrawer(GravityCompat.START)
       }

        binding.navigationMenu.itemIconTintList = null
        val navController : NavController = Navigation.findNavController(this,R.id.navHoster)
        NavigationUI.setupWithNavController(binding.navigationMenu,navController)





    }



    fun checkUser(){
        if(firebaseAuth.currentUser == null){
            goHome()
        }
    }

    fun goHome(){
        Intent(this, AuthActivity::class.java).also {
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