package com.kluivert.otuuna.repository

import android.app.Application
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kluivert.otuuna.data.UserModel
import com.kluivert.otuuna.utils.CustomDialog
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class AuthRepository(application: Application) {

    private lateinit var auth: FirebaseAuth
    private lateinit var application: Application
//    private val user = auth.currentUser
    private val personRef = Firebase.firestore.collection("Users")




    init {
        this.application = application
        this.auth = FirebaseAuth.getInstance()
    }


    fun saveDetails(userModel : UserModel){


            CoroutineScope(Dispatchers.IO).launch {
                CustomDialog(application.applicationContext).show()
                try {
                    personRef.add(userModel).await()
                    withContext(Dispatchers.Main){
                        CustomDialog(application.applicationContext).dismiss()
                        Toasty.success(application.applicationContext,"Success",Toast.LENGTH_SHORT).show()
                    }
                }catch (e : Exception){
                    withContext(Dispatchers.Main){
                        CustomDialog(application.applicationContext).dismiss()
                        Toasty.success(application.applicationContext,e.message.toString(),Toast.LENGTH_SHORT).show()
                    }


                }

            }
    }



}