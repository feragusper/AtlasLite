package com.feragusper.atlaslite.features.countries

import com.feragusper.atlaslite.AndroidTest
import com.feragusper.atlaslite.common.functional.Either
import com.feragusper.atlaslite.countries.android.viewmodel.CountryDetailsViewModel
import com.feragusper.atlaslite.countries.domain.Country
import com.feragusper.atlaslite.countries.interactor.GetCountryDetailsUseCase
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.given
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class CountryDetailsViewModelTest : AndroidTest() {

    private lateinit var countryDetailsViewModel: CountryDetailsViewModel

    @Mock
    private lateinit var getCountryDetailsUseCase: GetCountryDetailsUseCase

    @Before
    fun setUp() {
        countryDetailsViewModel = CountryDetailsViewModel(getCountryDetailsUseCase)
    }

    @Test
    fun `loading country details should update live data`() {
        val countryDetails = Country(
            id = "ar",
            name = "Argentina",
            code = "ar",
            capital = "Buenos Aires",
            area = 15000000F,
            region = "America",
            subregion = "South America",
            population = 40000000,
            demonym = "Argentinian",
            timezones = emptyList(),
            callingCodes = emptyList()
        )
        given { runBlocking { getCountryDetailsUseCase.run(eq(any())) } }.willReturn(Either.Right(countryDetails))

        countryDetailsViewModel.countryDetails.observeForever {
            with(it!!) {
                id shouldEqualTo "ar"
                name shouldEqualTo "Argentina"
                code shouldEqualTo "ar"
                capital shouldEqualTo "Buenos Aires"
                area shouldEqualTo 15000000F
                region shouldEqualTo "America"
                subregion shouldEqualTo "South America"
                population shouldEqualTo 40000000
                demonym shouldEqualTo "Argentinian"
            }
        }

        runBlocking { countryDetailsViewModel.loadCountryDetails("ar") }
    }
}