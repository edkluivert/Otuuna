package com.kluivert.otuuna.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class AuthRepository(application: Application) {

    private lateinit var auth: FirebaseAuth
    private lateinit var application: Application

    private var userLiveData: MutableLiveData<FirebaseUser>? = null
    private var loggedOutLiveData: MutableLiveData<Boolean>? = null


    init {
        this.application = application
        this.auth = FirebaseAuth.getInstance()
        this.userLiveData = MutableLiveData()
        this.loggedOutLiveData = MutableLiveData()

        if (auth.getCurrentUser() != null) {
            userLiveData!!.postValue(auth.getCurrentUser())
            loggedOutLiveData!!.postValue(false)
        }

    }



    fun registerUser(email: String, password: String){

        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.createUserWithEmailAndPassword(email, password).await()

            }catch (e: Exception){

            }
        }

    }

    fun loginUser(email: String, password: String){
        CoroutineScope(Dispatchers.IO).launch {
            try {
               auth.signInWithEmailAndPassword(email,password).await()
             //  userLiveData!!.postValue(auth.currentUser)
            }catch(e:Exception){

            }
        }

    }





}