package com.feragusper.atlaslite.countries.data.network

import com.feragusper.atlaslite.countries.domain.Country

data class CountryEntity(
    private val id: Int,
    private val name: String,
    private val alpha2Code: String
) {
    fun toCountry() = Country(
        id = id,
        name = name,
        code = alpha2Code
    )
}

