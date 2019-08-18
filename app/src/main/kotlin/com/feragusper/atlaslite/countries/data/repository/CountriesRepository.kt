package com.feragusper.atlaslite.countries.data.repository

import com.feragusper.atlaslite.common.exception.Failure
import com.feragusper.atlaslite.common.functional.Either
import com.feragusper.atlaslite.countries.data.network.CountriesService
import com.feragusper.atlaslite.countries.domain.Country
import retrofit2.Call
import javax.inject.Inject

interface CountriesRepository {
    fun movies(): Either<Failure, List<Country>>

    class CountriesRepositoryImpl
    @Inject constructor(
        private val service: CountriesService,
        private val moviesCache: CountriesCache
    ) : CountriesRepository {

        override fun movies(): Either<Failure, List<Country>> {
            val result = request(
                service.countries(),
                { countryEntityList -> countryEntityList.map { countryEntity -> countryEntity.toCountry() } },
                emptyList()
            )

            when (result) {
                is Either.Right -> moviesCache.putAll(result.b)
            }

            return result
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
