package com.kluivert.otuuna.Appfragments

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
import androidx.paging.PagedList
import coil.load
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.kluivert.kwota.util.OtuunaListener
import com.kluivert.otuuna.adapters.savedevents.SavedEventsAdapter
import com.kluivert.otuuna.data.OtuunaEvents
import com.kluivert.otuuna.data.UserModel
import com.kluivert.otuuna.databinding.FragmentDashboardBinding
import com.kluivert.otuuna.ui.activities.EventsActivity
import com.kluivert.otuuna.utils.EventsInterface
import com.kluivert.otuuna.utils.AppUtils
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


    private val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPrefetchDistance(2)
            .setPageSize(5)
            .build()

    private val database = FirebaseFirestore.getInstance()
    private val mQuery = database.collection("Events")

    // Init adapter options
    private val options = FirestorePagingOptions.Builder<OtuunaEvents>()
            .setLifecycleOwner(this)
            .setQuery(mQuery, config, OtuunaEvents::class.java)
            .build()


    private var evenAdapter = SavedEventsAdapter(options, this,this)

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




        binding.dashrecyevents.apply {
          //  val layoutManager = LinearLayoutManager(requireContext())
       //     layoutManager.orientation = LinearLayoutManager.HORIZONTAL
         //   binding.dashrecyevents.itemAnimator = DefaultItemAnimator()
            adapter = evenAdapter
        }


        binding.dashsrlayoutMain.setOnRefreshListener {
            evenAdapter.refresh()
        }



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
                binding.shimmerFrameLayout.stopShimmerAnimation()
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

    override suspend fun savelistener(otuunaEvents: OtuunaEvents, position: Int) {

    }

    override suspend fun viewListener(otuunaEvents: OtuunaEvents, position: Int) {

    }

    override fun refreshLayout() {
        binding.dashsrlayoutMain.isRefreshing = true
    }

    override fun stopRefreshingLayout() {
      binding.dashsrlayoutMain.isRefreshing = false
    }

    override fun shimmerStart() {
        TODO("Not yet implemented")
    }

    override fun shimmerStop() {
        TODO("Not yet implemented")
    }


}