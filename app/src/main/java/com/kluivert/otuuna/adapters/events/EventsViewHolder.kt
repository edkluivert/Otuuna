package com.kluivert.otuuna.adapters.events

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kluivert.otuuna.data.OtuunaEvents
import com.kluivert.otuuna.databinding.EventsItemLayoutBinding


class EventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

   val binding = EventsItemLayoutBinding.bind(itemView)


    fun bind(otuunaEvents: OtuunaEvents) {
      binding.apply {
          eventTitle.text = otuunaEvents.eventTitle
          eventTime.text = otuunaEvents.eventTime
          eventPosterImage.load(otuunaEvents.eventImage)


      }
    }

}