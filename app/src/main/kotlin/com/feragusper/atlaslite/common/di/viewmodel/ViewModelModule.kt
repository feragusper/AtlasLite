package com.feragusper.atlaslite.common.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.feragusper.atlaslite.countries.android.viewmodel.CountriesViewModel
import com.feragusper.atlaslite.countries.android.viewmodel.CountryDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Module to provide the ViewModels
 */
@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CountriesViewModel::class)
    abstract fun bindsCountriesViewModel(countriesViewModel: CountriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CountryDetailsViewModel::class)
    abstract fun bindsCountryDetailsViewModel(countryDetailsViewModel: CountryDetailsViewModel): ViewModel
}