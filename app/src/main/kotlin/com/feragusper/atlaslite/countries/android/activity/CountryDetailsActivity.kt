package com.feragusper.atlaslite.countries.android.activity

import android.content.Context
import android.content.Intent
import com.feragusper.atlaslite.common.android.BaseActivity
import com.feragusper.atlaslite.countries.android.fragment.CountryDetailsFragment

class CountryDetailsActivity : BaseActivity() {

    companion object {
        private const val INTENT_EXTRA_PARAM_COUNTRY_ID = "com.feragusper.imdblite.INTENT_EXTRA_PARAM_COUNTRY_ID"

        fun callingIntent(context: Context, countryId: String): Intent {
            val intent = Intent(context, CountryDetailsActivity::class.java)
            intent.putExtra(INTENT_EXTRA_PARAM_COUNTRY_ID, countryId)
            return intent
        }
    }

    override fun fragment() = CountryDetailsFragment.forCountry(
        intent.getStringExtra(
            INTENT_EXTRA_PARAM_COUNTRY_ID
        )
    )
}
