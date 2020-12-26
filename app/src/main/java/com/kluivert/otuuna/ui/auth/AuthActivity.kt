package com.kluivert.otuuna.ui.auth

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.kluivert.otuuna.R
import com.kluivert.otuuna.databinding.ActivityAuthBinding
import com.kluivert.otuuna.databinding.ActivityMainBinding
import com.kluivert.otuuna.ui.MainActivity
import com.kluivert.otuuna.viewModels.AuthViewModel
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthActivity : AppCompatActivity() {


    private lateinit var binding: ActivityAuthBinding

    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

       // firebaseAuth = FirebaseAuth.getInstance()
      //  authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)



        binding.loginButton.setOnClickListener {
            Intent(this,Login::class.java).also {
                startActivity(it)
            }
        }

        binding.signUpButton.setOnClickListener {
            Intent(this,SignUp::class.java).also {
                startActivity(it)
            }

        }

        transparentStatusBar()

    }


    fun Activity.transparentStatusBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.statusBarColor = Color.TRANSPARENT

        } else
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}