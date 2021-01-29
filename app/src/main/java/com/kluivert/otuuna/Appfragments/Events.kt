package com.kluivert.otuuna.Appfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagedList
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kluivert.kwota.util.OtuunaListener
import com.kluivert.otuuna.adapters.events.EventsAdapter

import com.kluivert.otuuna.data.OtuunaEvents
import com.kluivert.otuuna.databinding.FragmentEventsBinding
import com.kluivert.otuuna.utils.EventsInterface
import es.dmoral.toasty.Toasty


class Events : Fragment(), EventsInterface, OtuunaListener {


    private var _binding : FragmentEventsBinding? = null
    private val binding get() = _binding!!

    private  var personRef = Firebase.firestore
    private lateinit var auth: FirebaseAuth


    private val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPrefetchDistance(2)
            .setPageSize(10)
            .build()

    private val database = FirebaseFirestore.getInstance()
    private val mQuery = database.collection("Events")

    // Init adapter options
    private val options = FirestorePagingOptions.Builder<OtuunaEvents>()
            .setLifecycleOwner(this)
            .setQuery(mQuery, config, OtuunaEvents::class.java)
            .build()


    private var evenAdapter = EventsAdapter(options, this,this)

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
        personRef = FirebaseFirestore.getInstance()

        mQuery.get().addOnSuccessListener {
            val lastVisible = it.documents[it.size() - 1]
            val next = database.collection("Events")
                    .orderBy("eventTitle")
                    .startAfter(lastVisible)
                    .limit(25)
        }

        binding.recyevents.apply {
            adapter = evenAdapter
           // addOnScrollListener(this@Events.scrollListener)
        }
        binding.srlayoutMain.setOnRefreshListener { evenAdapter.refresh() }



    }

    private fun addEvents(){



    }



   /* var isScrolling = false
    var isLoading = false
    var isLastPage = false

    val scrollListener = object : RecyclerView.OnScrollListener(){

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = binding.recyevents.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition  = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount


            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >=  QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible
                    && isScrolling
            if (shouldPaginate){
                mQuery.get().addOnSuccessListener {
                    val lastVisible = it.documents[it.size() - 1]
                    val next = database.collection("Events")
                            .orderBy("eventTitle")
                            .startAfter(lastVisible)
                            .limit(25)
                }
                isScrolling = false
            }else{
                //binding.recyevents.setPadding(0,0,0,0)
            }


        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }
    }*/





    override fun shimmerStart() {
       binding.shimmerFrameLayout.startShimmerAnimation()
    }

    override fun shimmerStop() {
        binding.shimmerFrameLayout.stopShimmerAnimation()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.srlayoutMain.visibility = View.VISIBLE

    }


    override fun refreshLayout() {
           binding.srlayoutMain.isRefreshing = true
    }

    override fun stopRefreshingLayout() {
            binding.srlayoutMain.isRefreshing = false
    }

    override suspend fun savelistener(otuunaEvents: OtuunaEvents, position: Int) {
        personRef.collection("Users").document(auth.currentUser!!.uid).collection("Events").document(otuunaEvents.eventTitle.toString()).set(otuunaEvents)
                .addOnSuccessListener {
                    Toasty.success(requireContext(),"Added", Toast.LENGTH_SHORT,true).show()
                }
    }

    override suspend fun viewListener(otuunaEvents: OtuunaEvents, position: Int) {

    }
}