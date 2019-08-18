package com.feragusper.atlaslite.countries.android.activity

import android.content.Context
import android.content.Intent
import com.feragusper.atlaslite.common.android.BaseActivity
import com.feragusper.atlaslite.common.android.BaseFragment
import com.feragusper.atlaslite.countries.android.fragment.CountriesFragment

class CountriesActivity : BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context, CountriesActivity::class.java)
    }

    override fun fragment(): BaseFragment? {
        return CountriesFragment()
    }
}
