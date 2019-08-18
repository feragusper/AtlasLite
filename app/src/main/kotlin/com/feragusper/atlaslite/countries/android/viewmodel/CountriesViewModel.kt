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
@Inject constructor(private val getMoviesUseCase: GetCountriesUseCase) : BaseViewModel() {

    var countries: MutableLiveData<List<Country>> = MutableLiveData()

    fun loadCountries() = getMoviesUseCase(UseCase.None()) { it.either(::handleFailure, ::handleMovieList) }

    private fun handleMovieList(movies: List<Country>) {
        this.countries.value = movies
    }

}