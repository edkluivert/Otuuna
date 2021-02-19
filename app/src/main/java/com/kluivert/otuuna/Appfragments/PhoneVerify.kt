package com.kluivert.otuuna.Appfragments

import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.kluivert.otuuna.R
import com.kluivert.otuuna.databinding.FragmentOtpBinding
import com.kluivert.otuuna.databinding.FragmentPhoneVerifyBinding

class PhoneVerify : Fragment() {


    private var _binding : FragmentPhoneVerifyBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPhoneVerifyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        init()



    }

    private fun init(){

        val phonenum = binding.phoneeditext

        binding.ccp.registerCarrierNumberEditText(phonenum)
        binding.otp.text = Html.fromHtml(resources.getString(R.string.otp))
        binding.verifyBtn.setOnClickListener(View.OnClickListener {
            val number = binding.ccp.fullNumberWithPlus
            if (number?.isEmpty() == true) {
                phonenum.error = "Valid phon number is required"
                phonenum.requestFocus()
            } else {
               val action = PhoneVerifyDirections.actionPhoneVerifyToOtpFragment(phonenum.text.toString())
                findNavController().navigate(action)
            }
        })
    }
}