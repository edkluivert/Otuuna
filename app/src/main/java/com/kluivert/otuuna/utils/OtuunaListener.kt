package com.kluivert.kwota.util

import com.kluivert.otuuna.data.OtuunaEvents


interface OtuunaListener {


    suspend fun savelistener(otuunaEvents: OtuunaEvents, position: Int)

    suspend fun viewListener(otuunaEvents: OtuunaEvents, position: Int)

}