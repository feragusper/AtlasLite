package com.feragusper.atlaslite.countries.data.network

import retrofit2.Call
import retrofit2.http.GET

internal interface CountriesApi {
    companion object {
        private const val PATH_COUNTRIES = "all"
    }

    @GET(PATH_COUNTRIES)
    fun countries(): Call<List<CountryEntity>>
}
