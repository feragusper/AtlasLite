package com.feragusper.atlaslite.features.countries

import com.feragusper.atlaslite.UnitTest
import com.feragusper.atlaslite.common.functional.Either
import com.feragusper.atlaslite.countries.data.repository.CountriesRepository
import com.feragusper.atlaslite.countries.domain.Country
import com.feragusper.atlaslite.countries.interactor.GetCountryDetailsUseCase
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetCountryDetailsUseCaseTest : UnitTest() {

    private val COUNTRY_ID = "ar"

    private lateinit var getCountryDetailsUseCase: GetCountryDetailsUseCase

    @Mock
    private lateinit var countriesRepository: CountriesRepository

    @Before
    fun setUp() {
        getCountryDetailsUseCase = GetCountryDetailsUseCase(countriesRepository)
        given { countriesRepository.countryDetails(COUNTRY_ID) }.willReturn(Either.Right(Country.empty()))
    }

    @Test
    fun `should get data from repository`() {
        runBlocking { getCountryDetailsUseCase.run(GetCountryDetailsUseCase.Params(COUNTRY_ID)) }

        verify(countriesRepository).countryDetails(COUNTRY_ID)
        verifyNoMoreInteractions(countriesRepository)
    }
}
