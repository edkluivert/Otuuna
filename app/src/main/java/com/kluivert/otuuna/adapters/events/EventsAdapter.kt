package com.kluivert.otuuna.adapters.events

import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.kluivert.kwota.util.OtuunaListener
import com.kluivert.otuuna.R
import com.kluivert.otuuna.data.OtuunaEvents
import com.kluivert.otuuna.Appfragments.Events
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class EventsAdapter(options: FirestorePagingOptions<OtuunaEvents>, private val events : Events, var listener: OtuunaListener) :
        FirestorePagingAdapter<OtuunaEvents, EventsViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.events_item_layout, parent, false)
        return EventsViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: EventsViewHolder, position: Int, otuunaEvents: OtuunaEvents) {
        // Bind to ViewHolder
        viewHolder.bind(otuunaEvents)
        viewHolder.binding.svBtn.setOnClickListener {
            GlobalScope.launch {
                listener.savelistener(otuunaEvents,position)
            }
        }

        viewHolder.binding.eventCard.setOnClickListener {
                     listener.viewListener(otuunaEvents,position)
        }

    }

    override fun onLoadingStateChanged(state: LoadingState) {
        when (state) {
            LoadingState.LOADING_INITIAL -> {
             events.shimmerStart()
            }
            LoadingState.LOADED ->{
               events.shimmerStop()
                events.refreshLayout()
            }
            LoadingState.FINISHED,
            LoadingState.ERROR ->
                events.stopRefreshingLayout()
        }
    }


}