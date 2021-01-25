package com.kluivert.otuuna.Appfragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.kluivert.otuuna.R
import com.kluivert.otuuna.databinding.FragmentLoginBinding
import com.kluivert.otuuna.ui.MainActivity
import com.kluivert.otuuna.utils.AppUtils
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class Login : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
       _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        binding.shimmerViewContainer.startShimmerAnimation()

        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_register)
        }

        binding.tvForgot.setOnClickListener {
            resetPassword()
        }

    }

    private fun loginUser(){
        var email : String = binding.edemail.text.toString()
        var password : String = binding.edpassword.text.toString()

        if(password.length <= 5) {
            Toasty.warning(requireContext(),"Incorrect password", Toast.LENGTH_SHORT,true).show()
        }else{
            displayProgressBar(true)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(email,password).await()
                    withContext(Dispatchers.Main){
                        Toasty.success(requireContext(), "Success", Toast.LENGTH_SHORT,true).show()
                        goHome()
                    }
                }catch(e:Exception){

                    Toasty.error(requireContext(), e.message.toString(), Toast.LENGTH_SHORT,true).show()
                }
            }

        }

    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        binding.loginProgbar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    fun goHome(){
        startActivity(Intent(requireActivity(),MainActivity::class.java))
        AppUtils.animateEnterRight(requireActivity())
        activity?.finish()
        }


    private fun resetPassword(){
        val email = binding.edemail.text.toString()
        if (email.isEmpty()){
            Toasty.warning(requireContext(),"Enter email",Toast.LENGTH_SHORT,true).show()
        }else{
            auth.sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful){
                    Toasty.success(requireContext(),"Reset password link has been sent",Toast.LENGTH_SHORT,true).show()
                }else{
                    Toasty.error(requireContext(),it.exception.toString(),Toast.LENGTH_SHORT,true).show()
                }
            }
        }

    }

    }





