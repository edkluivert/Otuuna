package com.kluivert.otuuna.ui.mainFrags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.kluivert.otuuna.R
import com.kluivert.otuuna.data.UserModel
import com.kluivert.otuuna.databinding.FragmentDashboardBinding


class Dashboard : Fragment() {


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


        retrieveData()
        binding.shimmerFrameLayout.stopShimmer()

        binding.btnEdit.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_profileEdit)
        }

    }

    private fun retrieveData() {

        val id = auth.currentUser
        val docRef = personRef.collection("Users").document(id!!.uid)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val user = documentSnapshot.toObject<UserModel>()

            if (documentSnapshot.exists()){
                binding.tvName.text = user!!.firstName +""+ user.lastName
                binding.profileImage.load(user.profilePhoto)

                binding.shimmerFrameLayout.stopShimmer()
            }

        }

    }


}