package com.kluivert.otuuna.ui.mainFrags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kluivert.otuuna.adapters.EventsAdapter

import com.kluivert.otuuna.data.OtuunaEvents
import com.kluivert.otuuna.databinding.FragmentEventsBinding
import com.kluivert.otuuna.utils.DividerItemDecoration
import com.kluivert.otuuna.viewModels.EventsViewModel


class Events : Fragment() {


    private lateinit var eventsAdapter: EventsAdapter



    private var _binding : FragmentEventsBinding? = null
    private val binding get() = _binding!!

    //private  var personRef = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEventsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()


   binding.shimmerFrameLayout.startShimmer()


         val query = FirebaseFirestore.getInstance()
             .collection("Events")
             //.whereEqualTo("eventTitle","Home")
            .orderBy("eventTitle")
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPrefetchDistance(5)
            .setPageSize(10) // No of items loaded from datasource
            .build()
        val options = FirestorePagingOptions.Builder<OtuunaEvents>()
            .setQuery(query, config, OtuunaEvents::class.java)
            .build()


         eventsAdapter = EventsAdapter (options)

        binding.recyevents.layoutManager = LinearLayoutManager(requireContext())
        binding.recyevents.addItemDecoration(
                DividerItemDecoration(
                        requireContext(),
                        LinearLayoutManager.VERTICAL
                )

        )
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE


        binding.recyevents.adapter = eventsAdapter
         binding.recyevents.visibility = View.VISIBLE

    }



   override fun onStart() {
        super.onStart()
       eventsAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        eventsAdapter.stopListening()
    }


    companion object {
        @JvmStatic
        fun newInstance() = Events()
    }
}