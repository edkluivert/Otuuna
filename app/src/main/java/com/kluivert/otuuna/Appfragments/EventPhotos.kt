package com.kluivert.otuuna.Appfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.kluivert.otuuna.R
import com.kluivert.otuuna.databinding.FragmentDashboardBinding
import com.kluivert.otuuna.databinding.FragmentEventPhotosBinding

class EventPhotos : Fragment() {

    val args : EventPhotosArgs by navArgs()
    private var _binding : FragmentEventPhotosBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
       _binding = FragmentEventPhotosBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }


}