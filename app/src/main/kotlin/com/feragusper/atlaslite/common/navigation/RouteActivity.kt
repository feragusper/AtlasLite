package com.feragusper.atlaslite.common.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.feragusper.atlaslite.common.AtlasLiteApplication
import com.feragusper.atlaslite.common.di.ApplicationComponent
import javax.inject.Inject

/**
 * Entry point of the application. Just a transparent activity that handles what to do when the application is being launched.
 */
class RouteActivity : AppCompatActivity() {

    private val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as AtlasLiteApplication).appComponent
    }

    @Inject
    internal lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        navigator.showMain(this)
    }
}
