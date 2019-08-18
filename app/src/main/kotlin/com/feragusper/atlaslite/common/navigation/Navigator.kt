package com.feragusper.atlaslite.common.navigation

import android.content.Context
import android.view.View
import com.feragusper.atlaslite.countries.android.activity.CountriesActivity
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

    class Extras(val transitionSharedElement: View)
}


