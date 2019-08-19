package com.feragusper.atlaslite.countries.domain

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
)
