package com.kluivert.otuuna.utils

import com.kluivert.otuuna.data.OtuunaEvents

interface EventsListener{
   suspend fun onClick(otuunaEvents: OtuunaEvents)
}