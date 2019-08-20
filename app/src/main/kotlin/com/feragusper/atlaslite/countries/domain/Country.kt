package com.feragusper.atlaslite.countries.domain

import com.feragusper.atlaslite.common.extension.empty

data class Country(
    val id: String,
    val name: String,
    val code: String,
    val capital: String,
    val callingCodes: List<String>,
    val region: String,
    val subregion: String,
    val population: Int,
    val demonym: String,
    val area: Float,
    val timezones: List<String>
) {
    companion object {
        fun empty() = Country(
            id = String.empty(),
            name = String.empty(),
            code = String.empty(),
            capital = String.empty(),
            callingCodes = emptyList(),
            region = String.empty(),
            subregion = String.empty(),
            population = 0,
            demonym = String.empty(),
            area = 0F,
            timezones = emptyList()
        )
    }
}