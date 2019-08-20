package com.feragusper.atlaslite.countries.android.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import com.feragusper.atlaslite.R
import com.feragusper.atlaslite.common.android.BaseFragment
import com.feragusper.atlaslite.common.exception.Failure
import com.feragusper.atlaslite.common.extension.*
import com.feragusper.atlaslite.countries.android.CountryDetailsAnimator
import com.feragusper.atlaslite.countries.android.viewmodel.CountryDetailsViewModel
import com.feragusper.atlaslite.countries.domain.Country
import com.feragusper.atlaslite.countries.exception.CountryFailure
import com.feragusper.atlaslite.countries.extension.loadCountryFlag
import com.feragusper.atlaslite.countries.extension.makeCountryFlagUri
import kotlinx.android.synthetic.main.fragment_country_details.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class CountryDetailsFragment : BaseFragment() {

    companion object {
        private const val ARGUMENT_COUNTRY_ID = "com.feragusper.imdblite.ARGUMENT_COUNTRY_ID"

        fun forCountry(id: String?): CountryDetailsFragment {
            val countryDetailsFragment = CountryDetailsFragment()
            val arguments = Bundle()
            arguments.putString(ARGUMENT_COUNTRY_ID, id)
            countryDetailsFragment.arguments = arguments

            return countryDetailsFragment
        }
    }

    @Inject
    lateinit var countryDetailsAnimator: CountryDetailsAnimator

    private lateinit var countryDetailsViewModel: CountryDetailsViewModel

    override fun layoutId() = R.layout.fragment_country_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        activity?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                countryDetailsAnimator.postponeEnterTransition(it)
            }
        }

        countryDetailsViewModel = viewModel(viewModelFactory) {
            observe(countryDetails, ::renderCountryDetails)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (firstTimeCreated(savedInstanceState)) {
            countryDetailsViewModel.loadCountryDetails(arguments?.get(ARGUMENT_COUNTRY_ID) as String)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                countryDetailsAnimator.cancelTransition(countryFlag)
            }
            countryFlag.loadCountryFlag(arguments!![ARGUMENT_COUNTRY_ID] as Country)
        }
    }

    override fun onBackPressed() {
        countryDetailsAnimator.fadeInvisible(scrollView, countryDetails)
    }

    @SuppressLint("SetTextI18n")
    private fun renderCountryDetails(country: Country?) {
        country?.let {
            with(country) {
                activity?.let {
                    countryFlag.loadUrlAndPostponeEnterTransition(country.makeCountryFlagUri(), it)
                    it.toolbar.title = name
                }
                countryCapital.text = country.capital
                countryRegion.text = "${country.region} / ${country.subregion}"
                countryPopulation.text = "${country.population}"
                countryDemonym.text = country.demonym
                countryArea.text = "${country.area}"
            }
        }
        countryDetailsAnimator.fadeVisible(scrollView, countryDetails)
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.ServerError -> {
                notify(R.string.failure_server_error); close()
            }
            is CountryFailure.NonExistentCountry -> {
                notify(R.string.failure_country_non_existent); close()
            }
        }
    }
}
