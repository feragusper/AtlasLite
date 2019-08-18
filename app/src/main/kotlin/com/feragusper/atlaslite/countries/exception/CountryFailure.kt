package com.feragusper.atlaslite.countries.exception

import com.feragusper.atlaslite.common.exception.Failure

class CountryFailure {
    object ListNotAvailable : Failure.FeatureFailure()
    object NonExistentCountry : Failure.FeatureFailure()
}