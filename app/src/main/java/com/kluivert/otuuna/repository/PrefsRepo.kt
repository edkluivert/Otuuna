package com.kluivert.otuuna.repository

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


const val PREFERENCE_NAME = true

class PrefsRepo(context: Context) {

   private object preferencesKey{
      val name = preferencesKey<String>(true.toString())
   }


   private val datastore : DataStore<Preferences> = context.createDataStore(
       name = PREFERENCE_NAME.toString()
   )

  suspend fun saveToDataStore(name : String){
      datastore.edit { preference->
         preference[preferencesKey.name] = name
      }
   }

   val readFromDataStore : Flow<String> = datastore.data.catch { exception->
       if (exception is IOException){
           emit(emptyPreferences())
       }else{
           throw exception
       }

   }.map {preference->
       val myType = preference[preferencesKey.name] ?: "none"
       myType
   }


}