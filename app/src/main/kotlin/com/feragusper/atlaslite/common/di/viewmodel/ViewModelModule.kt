package com.feragusper.atlaslite.common.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.feragusper.atlaslite.countries.android.viewmodel.CountriesViewModel
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
    abstract fun bindsCountriesViewModel(moviesViewModel: CountriesViewModel): ViewModel
}