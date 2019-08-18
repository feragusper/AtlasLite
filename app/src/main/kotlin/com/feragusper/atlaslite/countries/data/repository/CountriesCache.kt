package com.feragusper.atlaslite.countries.data.repository

import android.content.Context
import android.util.LruCache
import com.feragusper.atlaslite.countries.domain.Country
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A cache for holding [Movie]s between Activities/Fragments.
 *
 * In order to get a cleaner architecture, we pass around as little data as possible,
 * in the case of a [Movie], that's a [MovieId].
 *
 * Then, the data is provided to components based on the [MovieId].
 *
 */
@Singleton
class CountriesCache @Inject constructor(val context: Context) {

    private val cache = LruCache<Int, Country>(1000)

    val hasMovies: Boolean
        get() = cache.size() != 0

    fun put(country: Country) {
        cache.put(country.id, country)
    }

    operator fun get(id: Int) = cache.get(id)

    fun putAll(countries: List<Country>) {
        countries.forEach { country ->
            put(country)
        }
    }
}