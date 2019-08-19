package com.feragusper.atlaslite.countries.data.repository

import com.feragusper.atlaslite.common.exception.Failure
import com.feragusper.atlaslite.common.functional.Either
import com.feragusper.atlaslite.countries.data.network.CountriesService
import com.feragusper.atlaslite.countries.domain.Country
import com.feragusper.atlaslite.countries.exception.CountryFailure
import retrofit2.Call
import javax.inject.Inject

interface CountriesRepository {
    fun countries(): Either<Failure, List<Country>>
    fun countryDetails(id: String): Either<Failure, Country>

    class CountriesRepositoryImpl
    @Inject constructor(
        private val service: CountriesService,
        private val countriesCache: CountriesCache
    ) : CountriesRepository {

        override fun countries(): Either<Failure, List<Country>> {
            val result = request(
                service.countries(),
                { countryEntityList -> countryEntityList.map { countryEntity -> countryEntity.toCountry() } },
                emptyList()
            )

            when (result) {
                is Either.Right -> countriesCache.putAll(result.b)
            }

            return result
        }

        override fun countryDetails(id: String): Either<Failure, Country> {
            val country = countriesCache.get(id)
            return when (country) {
                is Country -> Either.Right(country)
                else -> Either.Left(CountryFailure.NonExistentCountry)
            }
        }

        private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> Either.Right(transform((response.body() ?: default)))
                    false -> Either.Left(Failure.ServerError)
                }
            } catch (exception: Throwable) {
                Either.Left(Failure.ServerError)
            }
        }
    }
}
