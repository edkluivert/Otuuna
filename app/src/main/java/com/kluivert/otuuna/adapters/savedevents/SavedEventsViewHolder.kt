package com.kluivert.otuuna.adapters.savedevents

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kluivert.otuuna.data.OtuunaEvents
import com.kluivert.otuuna.databinding.EventsItemLayoutBinding
import com.kluivert.otuuna.databinding.SavedEventsLayoutBinding


class SavedEventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

   val binding = SavedEventsLayoutBinding.bind(itemView)


    fun bind(otuunaEvents: OtuunaEvents) {
      binding.apply {
          savedTitle.text = otuunaEvents.eventTitle
          savedImage.load(otuunaEvents.eventImage)

      }
    }

}