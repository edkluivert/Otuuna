package com.kluivert.otuuna.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.kluivert.otuuna.R
import com.kluivert.otuuna.data.OtuunaEvents
import com.kluivert.otuuna.databinding.EventsItemLayoutBinding


class EventsAdapter(options: FirestorePagingOptions<OtuunaEvents>) : FirestorePagingAdapter<OtuunaEvents,EventsAdapter.EventsAdapterViewHolder >(options){

    inner class EventsAdapterViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
      val binding = EventsItemLayoutBinding.bind(itemView)
        fun bindUi(otuunaEvents: OtuunaEvents){
            binding.apply {
                eventTitle.text = otuunaEvents.eventTitle
                eventTime.text = otuunaEvents.eventTime
                eventPosterImage.load(otuunaEvents.eventImage)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsAdapterViewHolder {
        return EventsAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.events_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: EventsAdapterViewHolder,
        position: Int,
        model: OtuunaEvents
    ) {

     holder.bindUi(model)

    }


}