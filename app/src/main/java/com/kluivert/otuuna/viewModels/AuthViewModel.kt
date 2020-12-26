package com.kluivert.otuuna.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.kluivert.otuuna.repository.AuthRepository


class AuthViewModel(application: Application)  : AndroidViewModel(application) {

    private var authRepository: AuthRepository? = null
    private var userLiveData: MutableLiveData<FirebaseUser>? = null
    private var loggedOutLiveData: MutableLiveData<Boolean>? = null



    init {
        this.authRepository = AuthRepository(application)
        userLiveData = authRepository!!.getUserLiveData();
        loggedOutLiveData = authRepository!!.getLoggedOutLiveData();
    }


    fun registerUser(email : String, password : String){
        authRepository!!.registerUser(email,password)
    }

    fun loginUser(email: String,password: String){
        authRepository!!.loginUser(email,password)
    }

    fun logOut() {
        authRepository!!.logOut()
    }

    fun getUserLiveData(): MutableLiveData<FirebaseUser>? {
        return userLiveData
    }

    fun getLoggedOutLiveData(): MutableLiveData<Boolean>? {
        return loggedOutLiveData
    }
}