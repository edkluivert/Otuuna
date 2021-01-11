package com.kluivert.otuuna.ui.mainFrags

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.kluivert.otuuna.Appfragments.Register
import com.kluivert.otuuna.R
import com.kluivert.otuuna.data.UserModel
import com.kluivert.otuuna.databinding.FragmentDashboardBinding
import com.kluivert.otuuna.ui.EventsActivity
import com.kluivert.otuuna.ui.MainActivity
import com.kluivert.otuuna.utils.AppUtils
import es.dmoral.toasty.Toasty


class Dashboard : Fragment() {


    companion object{
            @JvmStatic
            fun newInstance() = Dashboard()


        const val IMAGE_REQUEST_CODE = 0
    }

    var curFile : Uri? = null

    private var _binding : FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private  var personRef = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      _binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        personRef = FirebaseFirestore.getInstance()

       binding.eventsSearch.setOnClickListener {
           startActivity(Intent(requireActivity(), EventsActivity::class.java))
           AppUtils.animateEnterRight(requireActivity())
           activity?.finish()
       }



        retrieveData()



        binding.profileImage.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                startActivityForResult(it, IMAGE_REQUEST_CODE)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun retrieveData() {

        val id = auth.currentUser
        val docRef = personRef.collection("Users").document(id!!.uid)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val user = documentSnapshot.toObject<UserModel>()

            if (documentSnapshot.exists()){
                binding.shimmerFrameLayout.stopShimmer()
                binding.shimmerFrameLayout.visibility = View.GONE
                binding.mainCard.visibility = View.VISIBLE
                binding.tvName.text = user!!.firstName +" "+ user.lastName
                binding.profileImage.load(user.profilePhoto)
            }else{
                binding.shimmerFrameLayout.visibility = View.VISIBLE
                binding.mainCard.visibility = View.GONE
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == Register.IMAGE_REQUEST_CODE){
            data?.data?.let {
                curFile = it
                binding.profileImage.setImageURI(it)
                displayProfileProgBar(true)
                updatePhoto()

            }
        }

    }

    private fun displayProfileProgBar(isDisplayed: Boolean) {
        binding.profileProgbar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    fun updatePhoto(){

        val id = auth.currentUser

        val ref = Firebase.storage.reference.child("Images/").child(id!!.uid)
        val uploadTask = ref.putFile(curFile!!)
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                throw task.exception!!
            }


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
                            displayProfileProgBar(false)

                        }
                        .addOnFailureListener {
                            Toasty.error(requireContext(),it.message.toString(), Toast.LENGTH_SHORT,true).show()
                            displayProfileProgBar(false)
                        }

                displayProfileProgBar(false)

            } else {


            }
        }
    }


}