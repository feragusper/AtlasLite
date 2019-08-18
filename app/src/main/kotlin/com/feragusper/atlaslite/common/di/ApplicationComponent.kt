package com.feragusper.atlaslite.common.di

import com.feragusper.atlaslite.common.AtlasLiteApplication
import com.feragusper.atlaslite.common.di.viewmodel.ViewModelModule
import com.feragusper.atlaslite.common.navigation.RouteActivity
import com.feragusper.atlaslite.countries.android.fragment.CountriesFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Component to inject Application, Activities and Fragments
 */
@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: AtlasLiteApplication)
    fun inject(application: RouteActivity)
    fun inject(countriesFragment: CountriesFragment)
}
