package com.feragusper.atlaslite.countries.android

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feragusper.atlaslite.R
import com.feragusper.atlaslite.common.extension.inflate
import com.feragusper.atlaslite.common.navigation.Navigator
import com.feragusper.atlaslite.countries.domain.Country
import kotlinx.android.synthetic.main.view_item_country.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class CountriesAdapter
@Inject constructor() : RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {

    internal var collection: List<Country> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal var itemClickListener: (Country, Navigator.Extras) -> Unit = { _, _ -> }

    internal var favoriteButtonClickListener: (Country, Boolean) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.view_item_country))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(
            country = collection[position],
            itemClickListener = itemClickListener
        )

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            country: Country,
            itemClickListener: (Country, Navigator.Extras) -> Unit
        ) {
            itemView.countryName.text = country.name
            itemView.setOnClickListener { itemClickListener(country, Navigator.Extras(itemView)) }
        }
    }
}
