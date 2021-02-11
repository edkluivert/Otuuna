package com.kluivert.otuuna.adapters.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.kluivert.kwota.util.OtuunaListener
import com.kluivert.otuuna.Appfragments.EventPhotos
import com.kluivert.otuuna.R
import com.kluivert.otuuna.data.OtuunaEvents
import com.kluivert.otuuna.Appfragments.Events
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class PhotosAdapter(options: FirestorePagingOptions<OtuunaEvents>, private val eventPhotos: EventPhotos, var listener: OtuunaListener) :
        FirestorePagingAdapter<OtuunaEvents, PhotoViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item_layout, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: PhotoViewHolder, position: Int, otuunaEvents: OtuunaEvents) {
        // Bind to ViewHolder
        viewHolder.bind(otuunaEvents)
        viewHolder.binding.btnSavePhotos.setOnClickListener {
            GlobalScope.launch {
                listener.savelistener(otuunaEvents,position)
            }
        }

        viewHolder.binding.imageUser.setOnClickListener {
                     listener.viewListener(otuunaEvents,position)
        }

    }

    override fun onLoadingStateChanged(state: LoadingState) {
        when (state) {
            LoadingState.LOADING_INITIAL -> {
             //events.shimmerStart()
            }
            LoadingState.LOADED ->{
             //  events.shimmerStop()
              //  events.refreshLayout()
            }
            LoadingState.FINISHED,
            LoadingState.ERROR ->{}
            //    events.stopRefreshingLayout()
        }
    }


}