package com.kluivert.otuuna.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kluivert.otuuna.data.UserModel
import com.kluivert.otuuna.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AuthViewModel(application: Application)  : AndroidViewModel(application) {

    private var authRepository: AuthRepository? = null



    init {
        this.authRepository = AuthRepository(application)
    }

      fun saveDetails(userModel: UserModel){
         viewModelScope.launch {
             authRepository!!.saveDetails(userModel)
         }
     }


}