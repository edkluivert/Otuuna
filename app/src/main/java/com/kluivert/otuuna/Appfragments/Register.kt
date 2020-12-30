package com.kluivert.otuuna.Appfragments

import android.Manifest
import android.app.Activity
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
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.kluivert.otuuna.R
import com.kluivert.otuuna.data.UserModel
import com.kluivert.otuuna.databinding.FragmentRegisterBinding
import com.kluivert.otuuna.ui.MainActivity
import com.kluivert.otuuna.utils.AppUtils
import com.theartofdev.edmodo.cropper.CropImage
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.security.Permission


class Register : Fragment() {


    companion object{
        const val IMAGE_REQUEST_CODE = 0
    }

    var curFile : Uri? = null

    private var  _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth
    private  var personRef = Firebase.firestore


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


        binding.profileImage.setOnClickListener {

            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                startActivityForResult(it, IMAGE_REQUEST_CODE)
            }
            requestPermission()
        }

        binding.btnRegister.setOnClickListener {
            registerUser()
        }


    }

    fun registerUser(){

        val email = binding.regEmail.text.toString().trim()
        val password = binding.regPassword.text.toString()
        val firstname = binding.regFirstname.text.toString().trim()
        val lastname = binding.regLastname.text.toString().trim()

        if (password.length <= 5) {
            Toasty.warning(requireContext(), "Password should be more than 5 characters ", Toast.LENGTH_SHORT, true).show()
        }else if(!emailCheck(email)){
            Toasty.warning(requireContext(), "Enter valid email!", Toast.LENGTH_SHORT, true).show()
        }else if(firstname.isEmpty()){
            Toasty.warning(requireContext(), "Enter first name", Toast.LENGTH_SHORT, true).show()
        }else if(lastname.isEmpty()) {
            Toasty.warning(requireContext(), "Enter last name", Toast.LENGTH_SHORT, true).show()
        }else if(binding.profileImage.drawable.constantState ==  resources.getDrawable(R.drawable.ic_add_a_photo).constantState){
            Toasty.warning(requireContext(), "Select a photo", Toast.LENGTH_SHORT, true).show()
        }else{
             displayProfileProgBar(true)
            displayProgressBar(true)

            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                if (it.isSuccessful){
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
        val firstname = binding.regFirstname.text.toString().trim()
        val lastname = binding.regLastname.text.toString().trim()

        val newUser = UserModel(firstname,lastname)

        val ref = Firebase.storage.reference.child("Images/$firstname").child(id)
        val uploadTask = ref.putFile(curFile!!)
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                throw task.exception!!
            }
             displayProfileProgBar(false)
          //  Toasty.success(requireContext(),"Image Uploaded",Toast.LENGTH_SHORT,true).show()

            // Continue with the task to getBitmap the download URL
            ref.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                newUser.profilePhoto =  task.result.toString()
                emailVerification()
                displayProfileProgBar(false)

               personRef.collection("Users").document(id).set(newUser).addOnSuccessListener {
                     displayProgressBar(false)
                     Toasty.success(requireContext(),"Welcome",Toast.LENGTH_SHORT).show()
                     goHome()
                }

            } else {


            }
        }

    }

    fun goHome(){
        startActivity(Intent(requireActivity(), MainActivity::class.java))
        AppUtils.animateEnterRight(requireActivity())
        activity?.finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == IMAGE_REQUEST_CODE){
            data?.data?.let {
                curFile = it
                binding.profileImage.setImageURI(it)
            }
        }

    }





    private fun hasReadExternalStorage() = ActivityCompat.checkSelfPermission(requireContext(),
        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED


    private fun hasWriteExternalStorage() = ActivityCompat.checkSelfPermission(requireContext(),
        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    private fun requestPermission(){
        var permissionToRequest = mutableListOf<String>()

        if (!hasReadExternalStorage()){
            permissionToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)

        }

        if(!hasWriteExternalStorage()){
            permissionToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (permissionToRequest.isNotEmpty()){
            ActivityCompat.requestPermissions(requireActivity(),permissionToRequest.toTypedArray(),0)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 0 && grantResults.isNotEmpty()){
            for (i in grantResults.indices){
                if(grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                }else{
                    Toasty.warning(requireContext(),"${permissions[i]} denied",Toast.LENGTH_SHORT,true).show()
                }
            }
        }
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


    private fun displayProfileProgBar(isDisplayed: Boolean) {
        binding.profileProgbar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }


    fun emailCheck(email :String) : Boolean{
        var char : CharSequence = email
        return (!TextUtils.isEmpty(char) && Patterns.EMAIL_ADDRESS.matcher(char).matches())
    }

}