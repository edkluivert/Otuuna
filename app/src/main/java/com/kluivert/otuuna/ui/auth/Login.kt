package com.kluivert.otuuna.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.kluivert.otuuna.R
import com.kluivert.otuuna.databinding.ActivityLoginBinding
import com.kluivert.otuuna.ui.MainActivity
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class Login : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    //lateinit var authViewModel: AuthViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseAuth = FirebaseAuth.getInstance()
       // authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)


        binding.shimmerViewContainer.startShimmer()

        binding.btnLogin.setOnClickListener {
            loginUser()
        }

    }

    private fun loginUser(){
        var email : String = binding.edemail.text.toString()
        var password : String = binding.edpassword.text.toString()

        if(password.length <= 5) {
            Toasty.warning(this,"Incorrect password", Toast.LENGTH_SHORT,true).show()
        }else{

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    firebaseAuth.signInWithEmailAndPassword(email,password).await()
                    withContext(Dispatchers.Main){
                        checkUser()
                        Toasty.success(this@Login, "Success", Toast.LENGTH_SHORT,true).show()
                    }
                }catch(e:Exception){
                    Toasty.error(this@Login, e.message.toString(), Toast.LENGTH_SHORT,true).show()
                }
            }

        }

    }

    fun checkUser(){
        if(firebaseAuth.currentUser != null){
            goHome()
        }
    }

    fun goHome(){
        Intent(this,MainActivity::class.java).also {
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(it)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left,
            R.anim.slide_out_right);
    }

}