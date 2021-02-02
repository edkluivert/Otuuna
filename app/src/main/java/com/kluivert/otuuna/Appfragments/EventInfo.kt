package com.kluivert.otuuna.Appfragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.kluivert.otuuna.R
import com.kluivert.otuuna.data.OtuunaEvents
import com.kluivert.otuuna.data.UserModel
import com.kluivert.otuuna.databinding.FragmentEventInfoBinding
import com.kluivert.otuuna.databinding.FragmentEventsBinding


class EventInfo : Fragment() {

          val args : EventInfoArgs by navArgs()

    private var _binding :FragmentEventInfoBinding? = null
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
      _binding = FragmentEventInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)


        auth = FirebaseAuth.getInstance()
        personRef = FirebaseFirestore.getInstance()


         retrieveData()

    }

    private fun retrieveData() {
         val eventID = args.eventID
        val docRef = personRef.collection("Events").document(eventID)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val events = documentSnapshot.toObject<OtuunaEvents>()

             if (documentSnapshot.exists()){

            binding.posterTitle.text = events!!.eventTitle
            binding.posterTime.text = events.eventTime
            binding.posterInfo.text = events.eventInfo
            binding.posterImage.load(events.eventImage)
        }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.eventsmenu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       when(item.itemId){
           R.id.miShare -> {

           }
       }

        return super.onOptionsItemSelected(item)
    }



}