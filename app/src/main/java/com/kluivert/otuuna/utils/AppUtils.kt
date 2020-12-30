package com.kluivert.otuuna.utils

import android.app.Activity
import com.kluivert.otuuna.R

object AppUtils {


    fun animateEnterRight(activity: Activity) {
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    fun animateEnterLeft(activity: Activity) {
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    fun animateFadein(activity: Activity) {
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

}