package com.feragusper.atlaslite.countries.interactor

import com.feragusper.atlaslite.common.interactor.UseCase
import com.feragusper.atlaslite.countries.data.repository.CountriesRepository
import com.feragusper.atlaslite.countries.domain.Country
import javax.inject.Inject

class GetCountryDetailsUseCase
@Inject constructor(private val countriesRepository: CountriesRepository) :
    UseCase<Country, GetCountryDetailsUseCase.Params>() {

    override suspend fun run(params: Params) = countriesRepository.countryDetails(params.id)

    data class Params(val id: String)
}
