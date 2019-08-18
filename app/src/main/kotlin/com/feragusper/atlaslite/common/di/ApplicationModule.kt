package com.feragusper.atlaslite.common.di

import android.content.Context
import com.feragusper.atlaslite.BuildConfig
import com.feragusper.atlaslite.common.AtlasLiteApplication
import com.feragusper.atlaslite.countries.data.repository.CountriesRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Module to provide everything-else things
 */
@Module
class ApplicationModule(private val application: AtlasLiteApplication) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.COUNTRIES_API_HOST)
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideCountriesRepository(dataSource: CountriesRepository.CountriesRepositoryImpl): CountriesRepository = dataSource
}
