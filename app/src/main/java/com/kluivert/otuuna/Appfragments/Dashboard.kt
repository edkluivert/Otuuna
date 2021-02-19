package com.kluivert.otuuna.Appfragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.kluivert.kwota.util.OtuunaListener
import com.kluivert.otuuna.R
import com.kluivert.otuuna.adapters.savedevents.SavedEventsAdapter
import com.kluivert.otuuna.data.OtuunaEvents
import com.kluivert.otuuna.data.UserModel
import com.kluivert.otuuna.databinding.FragmentDashboardBinding
import com.kluivert.otuuna.ui.activities.EventsActivity
import com.kluivert.otuuna.utils.AppUtils
import com.kluivert.otuuna.utils.EventsInterface
import es.dmoral.toasty.Toasty


class Dashboard : Fragment() , EventsInterface, OtuunaListener{


    companion object{
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


        val id = auth.currentUser

       val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPrefetchDistance(2)
                .setPageSize(5)
                .build()

       val database = FirebaseFirestore.getInstance()
        val mQuery = database.collection("Users").document(id!!.uid).collection("Events")

        // Init adapter options
        val options = FirestorePagingOptions.Builder<OtuunaEvents>()
                .setLifecycleOwner(this)
                .setQuery(mQuery, config, OtuunaEvents::class.java)
                .build()


         var evenAdapter = SavedEventsAdapter(options, this, this)


        binding.uploadcard.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_events)
        }


    }


    override suspend fun savelistener(otuunaEvents: OtuunaEvents, position: Int) {

    }

    override  fun viewListener(otuunaEvents: OtuunaEvents, position: Int) {

    }

    override fun refreshLayout() {
       // binding.dashsrlayoutMain.isRefreshing = true
    }

    override fun stopRefreshingLayout() {
     // binding.dashsrlayoutMain.isRefreshing = false
    }

    override fun shimmerStart() {
        TODO("Not yet implemented")
    }

    override fun shimmerStop() {
        TODO("Not yet implemented")
    }


}