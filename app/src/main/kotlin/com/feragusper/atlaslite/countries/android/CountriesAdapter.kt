package com.feragusper.atlaslite.countries.android

import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.feragusper.atlaslite.R
import com.feragusper.atlaslite.common.extension.inflate
import com.feragusper.atlaslite.common.navigation.Navigator
import com.feragusper.atlaslite.countries.domain.Country
import com.feragusper.atlaslite.countries.extension.loadCountryFlag
import kotlinx.android.synthetic.main.view_item_country.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class CountriesAdapter
@Inject constructor() : RecyclerView.Adapter<CountriesAdapter.ViewHolder>(), Filterable {

    internal var collectionFiltered: List<Country> = emptyList()

    internal var collection: List<Country> by Delegates.observable(emptyList()) { _, _, _ ->
        collectionFiltered = collection
        notifyDataSetChanged()
    }

    internal var itemClickListener: (Country, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.view_item_country))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(
            country = collectionFiltered[position],
            itemClickListener = itemClickListener
        )

    override fun getItemCount() = collectionFiltered.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            country: Country,
            itemClickListener: (Country, Navigator.Extras) -> Unit
        ) {
            itemView.countryName.text = country.name
            itemView.countryFlag.loadCountryFlag(country)
            itemView.setOnClickListener { itemClickListener(country, Navigator.Extras(itemView.countryFlag)) }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    collectionFiltered = collection
                } else {
                    val filteredList = mutableListOf<Country>()
                    for (country in collection) {
                        if (country.name.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(country)
                        }
                    }

                    collectionFiltered = filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = collectionFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                @Suppress("UNCHECKED_CAST")
                collectionFiltered = filterResults.values as List<Country>
                notifyDataSetChanged()
            }
        }
    }
}
