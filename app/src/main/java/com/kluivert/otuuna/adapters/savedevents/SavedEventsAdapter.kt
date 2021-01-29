package com.kluivert.otuuna.adapters.savedevents

import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.kluivert.kwota.util.OtuunaListener
import com.kluivert.otuuna.R
import com.kluivert.otuuna.data.OtuunaEvents
import com.kluivert.otuuna.Appfragments.Dashboard
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SavedEventsAdapter(options: FirestorePagingOptions<OtuunaEvents>, private val dashboard: Dashboard, var listener: OtuunaListener) :
        FirestorePagingAdapter<OtuunaEvents, SavedEventsViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedEventsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.saved_events_layout, parent, false)
        return SavedEventsViewHolder(view)
    }

    override fun onBindViewHolder(viewHolderSaved: SavedEventsViewHolder, position: Int, otuunaEvents: OtuunaEvents) {
        // Bind to ViewHolder
        viewHolderSaved.bind(otuunaEvents)
        viewHolderSaved.binding.savedCard.setOnClickListener {
            GlobalScope.launch {
                listener.viewListener(otuunaEvents,position)
            }
        }

    }

    override fun onLoadingStateChanged(state: LoadingState) {
        when (state) {
            LoadingState.LOADING_INITIAL -> {
             dashboard.shimmerStart()

            }
            LoadingState.LOADED ->{
              dashboard.refreshLayout()

            }
            LoadingState.FINISHED,
            LoadingState.ERROR -> dashboard.stopRefreshingLayout()
        }
    }


}