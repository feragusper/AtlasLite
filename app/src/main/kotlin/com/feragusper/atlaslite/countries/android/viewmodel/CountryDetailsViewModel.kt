package com.feragusper.atlaslite.countries.android.viewmodel

import androidx.lifecycle.MutableLiveData
import com.feragusper.atlaslite.common.android.BaseViewModel
import com.feragusper.atlaslite.countries.domain.Country
import com.feragusper.atlaslite.countries.interactor.GetCountryDetailsUseCase
import javax.inject.Inject

class CountryDetailsViewModel
@Inject constructor(private val getCountryDetailsUseCase: GetCountryDetailsUseCase) : BaseViewModel() {

    var countryDetails: MutableLiveData<Country> = MutableLiveData()

    fun loadCountryDetails(countryId: String) =
        getCountryDetailsUseCase(GetCountryDetailsUseCase.Params(countryId)) {
            it.either(
                ::handleFailure,
                ::handleCountryDetails
            )
        }

    private fun handleCountryDetails(country: Country) {
        this.countryDetails.value = country
    }
}