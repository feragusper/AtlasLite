package com.feragusper.atlaslite.countries.data.network

import com.feragusper.atlaslite.countries.domain.Country

data class CountryEntity(
    private val name: String,
    private val alpha2Code: String,
    private val alpha3Code: String,
    private val capital: String,
    private val callingCodes: List<String>,
    private val region: String,
    private val subregion: String,
    private val population: Int,
    private val demonym: String,
    private val area: Float,
    private val timezones: List<String>
) {
    fun toCountry() = Country(
        id = alpha3Code,
        name = name,
        code = alpha2Code,
        capital = capital,
        callingCodes = callingCodes,
        region = region,
        subregion = subregion,
        population = population,
        demonym = demonym,
        area = area,
        timezones = timezones
    )
}

