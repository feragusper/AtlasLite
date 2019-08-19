package com.feragusper.atlaslite.countries.android

import android.os.Build
import android.transition.Fade
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.feragusper.atlaslite.common.extension.cancelTransition
import javax.inject.Inject


class CountryDetailsAnimator
@Inject constructor() {

    private val TRANSITION_DELAY = 200L
    private val TRANSITION_DURATION = 400L

    private val SCALE_UP_VALUE = 1.0F
    private val SCALE_UP_DURATION = 400L

    private val SCALE_DOWN_VALUE = 0.0F
    private val SCALE_DOWN_DURATION = 200L

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    internal fun postponeEnterTransition(activity: FragmentActivity) = activity.postponeEnterTransition()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    internal fun cancelTransition(view: View) = view.cancelTransition()

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    internal fun fadeVisible(viewContainer: ViewGroup, view: View) =
        beginTransitionFor(viewContainer, view, View.VISIBLE)

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    internal fun fadeInvisible(viewContainer: ViewGroup, view: View) =
        beginTransitionFor(viewContainer, view, View.INVISIBLE)

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun scaleView(view: View, x: Float, y: Float, duration: Long) =
        view.animate()
            .scaleX(x)
            .scaleY(y)
            .setDuration(duration)
            .setInterpolator(FastOutSlowInInterpolator())
            .withLayer()
            .setListener(null)
            .start()

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun beginTransitionFor(viewContainer: ViewGroup, view: View, visibility: Int) {
        val transition = Fade()
        transition.startDelay = TRANSITION_DELAY
        transition.duration = TRANSITION_DURATION
        TransitionManager.beginDelayedTransition(viewContainer, transition)
        view.visibility = visibility
    }
}


