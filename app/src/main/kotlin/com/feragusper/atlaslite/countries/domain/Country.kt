package com.feragusper.atlaslite.countries.domain

import com.feragusper.atlaslite.common.extension.empty

data class Country(
    val id: Int,
    val name: String
) {

    companion object {
        fun empty() = Country(
            id = 0,
            name = String.empty()
        )
    }
}
