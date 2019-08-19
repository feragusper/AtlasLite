package com.feragusper.atlaslite.common.navigation

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.FragmentActivity
import com.feragusper.atlaslite.countries.android.activity.CountriesActivity
import com.feragusper.atlaslite.countries.android.activity.CountryDetailsActivity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Helper that handles the navigation/transitions between screens within the application
 */
@Singleton
class Navigator
@Inject constructor() {

    fun showMain(context: Context) {
        showCountries(context)
    }

    private fun showCountries(context: Context) = context.startActivity(CountriesActivity.callingIntent(context))

    fun showCountryDetails(activity: FragmentActivity, id: String, navigationExtras: Extras) {
        val intent = CountryDetailsActivity.callingIntent(activity, id)
        val sharedView = navigationExtras.transitionSharedElement as ImageView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val activityOptions =
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sharedView, sharedView.transitionName)
            activity.startActivity(intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
        }
    }

    class Extras(val transitionSharedElement: View)
}


