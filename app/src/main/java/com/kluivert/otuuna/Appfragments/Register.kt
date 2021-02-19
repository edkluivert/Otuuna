package com.kluivert.otuuna.Appfragments

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.kluivert.otuuna.R
import com.kluivert.otuuna.data.UserModel
import com.kluivert.otuuna.databinding.FragmentRegisterBinding
import com.kluivert.otuuna.ui.activities.MainActivity
import com.kluivert.otuuna.utils.AppUtils
import com.kluivert.otuuna.viewModels.TypeViewModel
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class Register : Fragment() {


    private var  _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth
    private  var personRef = Firebase.firestore

    private lateinit var typeViewModel : TypeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        personRef = FirebaseFirestore.getInstance()
           typeViewModel = ViewModelProvider(this).get(TypeViewModel::class.java)



        binding.btnRegister.setOnClickListener {
            registerUser()
        }


    }

    fun registerUser(){

        val email = binding.regEmail.text.toString().trim()
        val password = binding.regPassword.text.toString()
        val firstname = binding.regFullname.text.toString().trim()
        val lastname = binding.regPhone.text.toString().trim()



        if (password.length <= 5) {
            Toasty.warning(requireContext(), "Password should be more than 5 characters ", Toast.LENGTH_SHORT, true).show()
        }else if(!emailCheck(email)){
            Toasty.warning(requireContext(), "Enter valid email!", Toast.LENGTH_SHORT, true).show()
        }else if(firstname.isEmpty()){
            Toasty.warning(requireContext(), "Enter first name", Toast.LENGTH_SHORT, true).show()
        }else if(lastname.isEmpty()) {
            Toasty.warning(requireContext(), "Enter last name", Toast.LENGTH_SHORT, true).show()

        }else{

            displayProgressBar(true)

            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                if (it.isSuccessful){
                    emailVerification()
                    val user = it.result!!.user
                    updateUi(user!!)

                }else{
                    displayProgressBar(false)
                }
            }

        }
    }


    private fun updateUi(user: FirebaseUser){
        val id = user.uid
        val fullname = binding.regFullname.text.toString().trim()
        val phone = binding.regPhone.text.toString().trim()

        val newUser = UserModel(fullname,phone)
        personRef.collection("Users").document(id).set(newUser).addOnSuccessListener {
            displayProgressBar(false)
            Toasty.success(requireContext(),"Welcome",Toast.LENGTH_SHORT).show()
            goHome()
        }


    }

   /* fun goHome(){
        findNavController().navigate(R.id.action_register_to_phoneVerify)
    }*/


    fun goHome(){
        startActivity(Intent(requireActivity(), MainActivity::class.java))
        AppUtils.animateEnterRight(requireActivity())
        activity?.finish()
    }




    private fun emailVerification(){
        val user : FirebaseUser? = firebaseAuth.currentUser
        CoroutineScope(Dispatchers.IO).launch {
            try {
                user!!.sendEmailVerification().await()
                withContext(Dispatchers.Main){
                    Toasty.success(requireContext(),"Verification email has been sent to you",
                        Toast.LENGTH_SHORT,true).show()
                }
            }catch(e : Exception){
                Toasty.error(requireContext(),e.message.toString(), Toast.LENGTH_SHORT,true).show()
            }
        }
    }


    private fun displayProgressBar(isDisplayed: Boolean) {
        binding.regProgbar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }


    fun emailCheck(email :String) : Boolean{
        var char : CharSequence = email
        return (!TextUtils.isEmpty(char) && Patterns.EMAIL_ADDRESS.matcher(char).matches())
    }

}