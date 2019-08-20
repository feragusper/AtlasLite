package com.feragusper.atlaslite.features.countries

import com.feragusper.atlaslite.UnitTest
import com.feragusper.atlaslite.common.functional.Either
import com.feragusper.atlaslite.common.interactor.UseCase
import com.feragusper.atlaslite.countries.data.repository.CountriesRepository
import com.feragusper.atlaslite.countries.domain.Country
import com.feragusper.atlaslite.countries.interactor.GetCountriesUseCase
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetCountriesUseCaseTest : UnitTest() {

    private lateinit var getCountriesUseCase: GetCountriesUseCase

    @Mock
    private lateinit var countriesRepository: CountriesRepository

    @Before
    fun setUp() {
        getCountriesUseCase = GetCountriesUseCase(countriesRepository)
        given { countriesRepository.countries() }.willReturn(Either.Right(listOf(Country.empty())))
    }

    @Test
    fun `should get data from repository`() {
        runBlocking { getCountriesUseCase.run(UseCase.None()) }

        verify(countriesRepository).countries()
        verifyNoMoreInteractions(countriesRepository)
    }
}
