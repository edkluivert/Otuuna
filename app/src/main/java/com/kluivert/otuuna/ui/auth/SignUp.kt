package com.kluivert.otuuna.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.kluivert.otuuna.R
import com.kluivert.otuuna.databinding.ActivitySignUpBinding
import com.kluivert.otuuna.ui.MainActivity
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SignUp : AppCompatActivity() {


    private lateinit var binding : ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
   // lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        firebaseAuth = FirebaseAuth.getInstance()
       // authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

         binding.shimmerViewContainer.startShimmer()


        binding.btnSignUp.setOnClickListener {
            signUp()
        }


    }


    private fun signUp(){

        val email = binding.edemailsign.text.toString()
        val password = binding.edpasswordsign.text.toString()

        if (password.length <= 5) {
            Toasty.warning(this, "Password should be more than 5 characters ", Toast.LENGTH_SHORT, true).show()
        }else if(!emailCheck(email)){
            Toasty.warning(this, "Enter valid email!", Toast.LENGTH_SHORT, true).show()
        }else{

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main){
                        checkUser()
                        Toasty.success(this@SignUp, "Success", Toast.LENGTH_SHORT,true).show()
                    }
                }catch (e: Exception){
                    Toasty.error(this@SignUp, e.message.toString(), Toast.LENGTH_SHORT,true).show()
                }
            }

        }



    }

    fun emailCheck(email :String) : Boolean{
        var char : CharSequence = email
        return (!TextUtils.isEmpty(char) && Patterns.EMAIL_ADDRESS.matcher(char).matches())
    }

    fun checkUser(){
        if(firebaseAuth.currentUser != null){
            goHome()
        }
    }

    fun goHome(){
        Intent(this, MainActivity::class.java).also {
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