package com.feragusper.atlaslite.countries.interactor

import com.feragusper.atlaslite.common.interactor.UseCase
import com.feragusper.atlaslite.countries.data.repository.CountriesRepository
import com.feragusper.atlaslite.countries.domain.Country
import javax.inject.Inject

class GetCountriesUseCase
@Inject constructor(private val countriesRepository: CountriesRepository) : UseCase<List<Country>, UseCase.None>() {

    override suspend fun run(params: None) = countriesRepository.countries()
}
