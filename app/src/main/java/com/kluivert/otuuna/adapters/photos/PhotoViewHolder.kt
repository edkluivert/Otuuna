package com.kluivert.otuuna.adapters.photos

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kluivert.otuuna.data.OtuunaEvents
import com.kluivert.otuuna.databinding.EventsItemLayoutBinding
import com.kluivert.otuuna.databinding.ImageItemLayoutBinding


class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

   val binding = ImageItemLayoutBinding.bind(itemView)


    fun bind(otuunaEvents: OtuunaEvents) {
      binding.apply {
          tvPhotoName.text = otuunaEvents.eventTime
         imageUser.load(otuunaEvents.eventImage)
      }
    }

}