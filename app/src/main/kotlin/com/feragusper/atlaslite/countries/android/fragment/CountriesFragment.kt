package com.feragusper.atlaslite.countries.android.fragment

import android.app.SearchManager
import android.content.Context.SEARCH_SERVICE
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
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

        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        loadCountriesList()
    }


    private fun initializeView() {
        countryList.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        countryList.adapter = countriesAdapter
        countriesAdapter.itemClickListener = { country, navigationExtras ->
            navigator.showCountryDetails(activity!!, country.id, navigationExtras)
        }
    }

    private fun loadCountriesList() {
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
        notifyWithAction(message, R.string.action_refresh, ::loadCountriesList)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_countries, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = context?.getSystemService(SEARCH_SERVICE) as SearchManager?
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager!!.getSearchableInfo(activity?.componentName))
        searchView.maxWidth = Integer.MAX_VALUE

        // listening to search query text change
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // filter recycler view when query submitted
                countriesAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                // filter recycler view when text is changed
                countriesAdapter.filter.filter(query)
                return false
            }
        })
    }

}
