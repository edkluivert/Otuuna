package com.kluivert.otuuna.Appfragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.kluivert.otuuna.R
import com.kluivert.otuuna.data.UserModel
import com.kluivert.otuuna.data.userPhoto
import com.kluivert.otuuna.databinding.FragmentProfileEditBinding
import com.kluivert.otuuna.ui.MainActivity
import com.kluivert.otuuna.utils.AppUtils
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProfileEdit : Fragment() {


    companion object{
        const val IMAGE_REQUEST_CODE = 0
    }

    var curFile : Uri? = null

    private var _binding : FragmentProfileEditBinding? = null
    private val binding get() = _binding!!


    private lateinit var firebaseAuth: FirebaseAuth
    private  var personRef = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
      _binding = FragmentProfileEditBinding.inflate(layoutInflater, container, false)
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
      }


        binding.btnSave.setOnClickListener {
         updatePhoto()
        }

    }


    fun updatePhoto(){

        displayProgressBar(true)
        val id = firebaseAuth.currentUser
      //  val firstname = binding.saveFirstname.text.toString().trim()
     //   val lastname = binding.saveLastname.text.toString().trim()

        val newphoto = userPhoto()

        val ref = Firebase.storage.reference.child("Images/").child(id!!.uid)
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

               val link : String =  task.result.toString()

                val userRef = personRef.collection("Users").document(id.uid)
                userRef
                    .update("profilePhoto",link)
                    .addOnSuccessListener {
                        Toasty.success(requireContext(),"Success", Toast.LENGTH_SHORT,true).show()
                        goHome()
                    }
                    .addOnFailureListener {
                        Toasty.error(requireContext(),it.message.toString(), Toast.LENGTH_SHORT,true).show()
                    }

                displayProfileProgBar(false)

               /* personRef.collection("Users").document(id.uid)
                    .update(mapOf(
                        "firstName" to firstname,
                        "lastName" to lastname
                    )).addOnCompleteListener {
                        if (it.isSuccessful){
                            Toasty.success(requireContext(),"Success", Toast.LENGTH_SHORT,true).show()
                            goHome()
                        }else{
                            Toasty.error(requireContext(),it.exception!!.message.toString(), Toast.LENGTH_SHORT,true).show()
                        }
                    }*/

            } else {


            }
        }
    }

   /* fun updatePersonalInfo(){
        val id = firebaseAuth.currentUser
        val firstname  = binding.saveFirstname.text.toString()
        val lastname = binding.saveLastname.text.toString()
       personRef.collection("Users").document(id!!.uid)
            .update(mapOf(
                "firstName" to firstname,
                "lastName" to lastname
            )).addOnCompleteListener {
                if (it.isSuccessful){
                    Toasty.success(requireContext(),"Success", Toast.LENGTH_SHORT,true).show()
                    goHome()
                }else{
                    Toasty.error(requireContext(),it.exception!!.message.toString(), Toast.LENGTH_SHORT,true).show()
                }
           }
    }*/


    fun goHome(){
        startActivity(Intent(requireActivity(), MainActivity::class.java))
        AppUtils.animateEnterLeft(requireActivity())
        activity?.finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == Register.IMAGE_REQUEST_CODE){
            data?.data?.let {
                curFile = it
                binding.profileImage.setImageURI(it)
            }
        }

    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        binding.saveProgbar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }


    private fun displayProfileProgBar(isDisplayed: Boolean) {
        binding.profileProgbar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }
}