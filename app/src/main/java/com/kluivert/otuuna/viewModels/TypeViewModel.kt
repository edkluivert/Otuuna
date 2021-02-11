package com.kluivert.otuuna.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kluivert.otuuna.repository.PrefsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TypeViewModel(application: Application) :  AndroidViewModel(application) {

    private val prefsRepo = PrefsRepo(application)

    val readFromDatastore = prefsRepo.readFromDataStore.asLiveData()

    fun saveToDatastore(userType : String) = viewModelScope.launch(Dispatchers.IO) {
        prefsRepo.saveToDataStore(userType)
    }
}