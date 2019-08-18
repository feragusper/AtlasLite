package com.feragusper.atlaslite.common

import android.app.Application
import com.feragusper.atlaslite.BuildConfig
import com.feragusper.atlaslite.common.di.ApplicationComponent
import com.feragusper.atlaslite.common.di.ApplicationModule
import com.feragusper.atlaslite.common.di.DaggerApplicationComponent
import com.squareup.leakcanary.LeakCanary

class AtlasLiteApplication : Application() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
        this.initializeLeakDetection()
    }

    private fun injectMembers() = appComponent.inject(this)

    private fun initializeLeakDetection() {
        if (BuildConfig.DEBUG) LeakCanary.install(this)
    }
}
