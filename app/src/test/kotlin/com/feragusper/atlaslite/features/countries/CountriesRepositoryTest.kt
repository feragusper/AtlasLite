package com.feragusper.atlaslite.features.countries

import com.feragusper.atlaslite.UnitTest
import com.feragusper.atlaslite.common.exception.Failure
import com.feragusper.atlaslite.common.functional.Either
import com.feragusper.atlaslite.countries.data.network.CountriesService
import com.feragusper.atlaslite.countries.data.network.CountryEntity
import com.feragusper.atlaslite.countries.data.repository.CountriesCache
import com.feragusper.atlaslite.countries.data.repository.CountriesRepository
import com.feragusper.atlaslite.countries.domain.Country
import com.feragusper.atlaslite.countries.exception.CountryFailure
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import retrofit2.Call
import retrofit2.Response

class CountriesRepositoryTest : UnitTest() {

    private lateinit var countriesRepositoryImpl: CountriesRepository.CountriesRepositoryImpl

    @Mock
    private lateinit var service: CountriesService
    @Mock
    private lateinit var countriesCache: CountriesCache

    @Mock
    private lateinit var countriesCall: Call<List<CountryEntity>>
    @Mock
    private lateinit var countriesResponse: Response<List<CountryEntity>>

    @Before
    fun setUp() {
        countriesRepositoryImpl = CountriesRepository.CountriesRepositoryImpl(service, countriesCache)
    }

    @Test
    fun `should return empty list by default`() {
        given { countriesResponse.body() }.willReturn(null)
        given { countriesResponse.isSuccessful }.willReturn(true)
        given { countriesCall.execute() }.willReturn(countriesResponse)
        given { service.countries() }.willReturn(countriesCall)

        val countries = countriesRepositoryImpl.countries()

        countries shouldEqual Either.Right(emptyList<Country>())
        verify(service).countries()
    }

    @Test
    fun `should get country list from service`() {
        given { countriesResponse.body() }.willReturn(
            listOf(
                CountryEntity(
                    name = "Argentina",
                    alpha2Code = "ar",
                    alpha3Code = "ars",
                    capital = "Buenos Aires",
                    area = 15000000F,
                    region = "America",
                    subregion = "South America",
                    population = 40000000,
                    demonym = "Argentinian",
                    timezones = emptyList(),
                    callingCodes = emptyList()
                )
            )
        )
        given { countriesResponse.isSuccessful }.willReturn(true)
        given { countriesCall.execute() }.willReturn(countriesResponse)
        given { service.countries() }.willReturn(countriesCall)

        val countries = countriesRepositoryImpl.countries()

        countries shouldEqual Either.Right(
            listOf(
                Country(
                    id = "ars",
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
            )
        )
        verify(service).countries()
    }

    @Test
    fun `countries service should return server error if no successful response`() {
        val countries = countriesRepositoryImpl.countries()

        countries shouldBeInstanceOf Either::class.java
        countries.isLeft shouldEqual true
        countries.either({ failure -> failure shouldBeInstanceOf Failure.ServerError::class.java }, {})
    }

    @Test
    fun `countries request should catch exceptions`() {
        val countries = countriesRepositoryImpl.countries()

        countries shouldBeInstanceOf Either::class.java
        countries.isLeft shouldEqual true
        countries.either({ failure -> failure shouldBeInstanceOf Failure.ServerError::class.java }, {})
    }

    @Test
    fun `country details request should catch exceptions`() {
        val countryDetails = countriesRepositoryImpl.countryDetails("non existent")

        countryDetails shouldBeInstanceOf Either::class.java
        countryDetails.isLeft shouldEqual true
        countryDetails.either(
            { failure -> failure shouldBeInstanceOf CountryFailure.NonExistentCountry::class.java },
            {})
    }
}