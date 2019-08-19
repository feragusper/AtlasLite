package com.feragusper.atlaslite.countries.data.repository

import android.content.Context
import android.util.LruCache
import com.feragusper.atlaslite.countries.domain.Country
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A cache for holding [Country]s between Activities/Fragments.
 *
 * In order to get a cleaner architecture, we pass around as little data as possible,
 * in the case of a [Country], that's a [Country]'s Id.
 *
 * Then, the data is provided to components based on the [Country]'s Id.
 *
 */
@Singleton
class CountriesCache @Inject constructor(val context: Context) {

    private val cache = LruCache<String, Country>(1000)

    fun put(country: Country) {
        cache.put(country.id, country)
    }

    operator fun get(id: String) = cache.get(id)

    fun putAll(countries: List<Country>) {
        countries.forEach { country ->
            put(country)
        }
    }
}