package com.feragusper.atlaslite.countries.data.network

import android.annotation.SuppressLint
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountriesService
@Inject constructor(retrofit: Retrofit) {
    private val countriesApi by lazy { retrofit.create(CountriesApi::class.java) }

    @SuppressLint("SimpleDateFormat")
    fun countries() = countriesApi.countries()
}
