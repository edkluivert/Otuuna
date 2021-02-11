package com.kluivert.otuuna.adapters.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kluivert.otuuna.R
import com.kluivert.otuuna.data.Slides
import com.kluivert.otuuna.databinding.ItemOnboardingLayoutBinding

class OnBoardingAdapter(
    val slidelist : List<Slides>
) : RecyclerView.Adapter<OnBoardingAdapter.OnBoardingAdapterViewHolder>(){


    inner class OnBoardingAdapterViewHolder(item : View) : RecyclerView.ViewHolder(item){
        val binding = ItemOnboardingLayoutBinding.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingAdapterViewHolder {
        return OnBoardingAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_onboarding_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: OnBoardingAdapterViewHolder, position: Int) {

        val current = slidelist[position]
             holder.binding.apply {
                  otuunalogo.load(current.otuunalogo)
                 otunnaillustration.load(current.otuunaphoto)
                 otuunatext1.text = current.otuunatext1
                 otunnatext2.text = current.otuunatext2
             }
    }

    override fun getItemCount(): Int {
      return slidelist.size
    }


}