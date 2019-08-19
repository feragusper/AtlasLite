package com.feragusper.atlaslite.countries.android.viewmodel

import androidx.lifecycle.MutableLiveData
import com.feragusper.atlaslite.common.android.BaseViewModel
import com.feragusper.atlaslite.common.interactor.UseCase
import com.feragusper.atlaslite.countries.domain.Country
import com.feragusper.atlaslite.countries.interactor.GetCountriesUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountriesViewModel
@Inject constructor(private val getCountriesUseCase: GetCountriesUseCase) : BaseViewModel() {

    var countries: MutableLiveData<List<Country>> = MutableLiveData()

    fun loadCountries() = getCountriesUseCase(UseCase.None()) { it.either(::handleFailure, ::handleCountryList) }

    private fun handleCountryList(countries: List<Country>) {
        this.countries.value = countries
    }

}