package com.kluivert.otuuna.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.google.firebase.firestore.FirebaseFirestore
import com.kluivert.otuuna.data.FirestorePagingSource

class EventsViewModel : ViewModel() {

    val flow = Pager(PagingConfig(20)) {
        FirestorePagingSource(FirebaseFirestore.getInstance())
    }.flow.cachedIn(viewModelScope)
}