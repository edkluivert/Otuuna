package com.kluivert.otuuna.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.kluivert.otuuna.R
import com.kluivert.otuuna.databinding.LoadingBarBinding

class CustomDialog(context: Context) : Dialog(context) {

    init {
        setCancelable(false)
    }

    private lateinit var binding: LoadingBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding= LoadingBarBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        

    }
}