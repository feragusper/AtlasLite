package com.feragusper.atlaslite.countries.extension

import android.widget.ImageView
import com.feragusper.atlaslite.common.extension.loadFromUriString
import com.feragusper.atlaslite.countries.domain.Country

fun ImageView.loadCountryFlag(country: Country) {
    loadFromUriString("file:///android_asset/${country.code.toLowerCase()}.png")
}