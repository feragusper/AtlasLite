package com.feragusper.atlaslite.features.countries

import com.feragusper.atlaslite.AndroidTest
import com.feragusper.atlaslite.common.functional.Either
import com.feragusper.atlaslite.countries.android.viewmodel.CountriesViewModel
import com.feragusper.atlaslite.countries.domain.Country
import com.feragusper.atlaslite.countries.interactor.GetCountriesUseCase
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.given
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class CountriesViewModelTest : AndroidTest() {

    private lateinit var countriesViewModel: CountriesViewModel

    @Mock
    private lateinit var getCountriesUseCase: GetCountriesUseCase

    @Before
    fun setUp() {
        countriesViewModel = CountriesViewModel(getCountriesUseCase)
    }

    @Test
    fun `loading countries should update live data`() {
        val countriesList = listOf(
            Country(
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
            ),
            Country(
                id = "br",
                name = "Brazil",
                code = "br",
                capital = "Brasilia",
                area = 30000000F,
                region = "America",
                subregion = "South America",
                population = 2000000000,
                demonym = "Brazilian",
                timezones = emptyList(),
                callingCodes = emptyList()
            )
        )
        given { runBlocking { getCountriesUseCase.run(eq(any())) } }.willReturn(Either.Right(countriesList))

        countriesViewModel.countries.observeForever {
            it!!.size shouldEqualTo 2
            it[0].id shouldEqualTo "ar"
            it[0].name shouldEqualTo "Argentina"
            it[0].code shouldEqualTo "ar"
            it[0].capital shouldEqualTo "Buenos Aires"
            it[0].area shouldEqualTo 15000000F
            it[0].region shouldEqualTo "America"
            it[0].subregion shouldEqualTo "South America"
            it[0].population shouldEqualTo 40000000
            it[0].demonym shouldEqualTo "Argentinian"
            it[1].id shouldEqualTo "br"
            it[1].name shouldEqualTo "Brazil"
            it[1].code shouldEqualTo "br"
            it[1].capital shouldEqualTo "Brasilia"
            it[1].area shouldEqualTo 30000000F
            it[1].region shouldEqualTo "America"
            it[1].subregion shouldEqualTo "South America"
            it[1].population shouldEqualTo 2000000000
            it[1].demonym shouldEqualTo "Brazilian"
        }

        runBlocking { countriesViewModel.loadCountries() }
    }
}