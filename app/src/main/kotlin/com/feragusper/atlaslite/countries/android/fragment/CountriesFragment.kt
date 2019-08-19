package com.feragusper.atlaslite.countries.android.fragment

import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.feragusper.atlaslite.R
import com.feragusper.atlaslite.common.android.BaseFragment
import com.feragusper.atlaslite.common.exception.Failure
import com.feragusper.atlaslite.common.extension.*
import com.feragusper.atlaslite.common.navigation.Navigator
import com.feragusper.atlaslite.countries.android.CountriesAdapter
import com.feragusper.atlaslite.countries.android.viewmodel.CountriesViewModel
import com.feragusper.atlaslite.countries.domain.Country
import com.feragusper.atlaslite.countries.exception.CountryFailure
import kotlinx.android.synthetic.main.fragment_countries.*
import javax.inject.Inject

class CountriesFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var countriesAdapter: CountriesAdapter

    private lateinit var countriesViewModel: CountriesViewModel

    override fun layoutId() = R.layout.fragment_countries

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        countriesViewModel = viewModel(viewModelFactory) {
            observe(countries, ::renderCountryList)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        loadMoviesList()
    }


    private fun initializeView() {
        countryList.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        countryList.adapter = countriesAdapter
        countriesAdapter.itemClickListener = { country, navigationExtras ->
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    private fun loadMoviesList() {
        emptyView.invisible()
        countryList.visible()
        showProgress()
        countriesViewModel.loadCountries()
    }

    private fun renderCountryList(countries: List<Country>?) {
        countriesAdapter.collection = countries.orEmpty()
        hideProgress()
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is Failure.ServerError -> renderFailure(R.string.failure_server_error)
            is CountryFailure.ListNotAvailable -> renderFailure(R.string.failure_country_list_unavailable)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        countryList.invisible()
        emptyView.visible()
        hideProgress()
        notifyWithAction(message, R.string.action_refresh, ::loadMoviesList)
    }
}
