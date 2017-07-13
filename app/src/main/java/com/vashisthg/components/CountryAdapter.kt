package com.vashisthg.components

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.vashisthg.components.model.Country

/**
 * Created by gauravvashisth on 26/05/17.
 */
class CountryAdapter : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {


    var countryList: List<Country>? = null

    fun setProductList(newCountryList: List<Country>?) {
        if (this.countryList == null) {
            this.countryList = newCountryList
            notifyItemRangeInserted(0, newCountryList?.size ?: 0)
        } else {
            var result: DiffUtil.DiffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return countryList?.get(oldItemPosition)?.getId() == newCountryList?.get(newItemPosition)?.getId()
                }

                override fun getOldListSize(): Int {
                    return countryList?.size ?: 0
                }

                override fun getNewListSize(): Int {
                    return newCountryList?.size ?: 0
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldCountry = countryList?.get(oldItemPosition)
                    val newCountry = newCountryList?.get(newItemPosition)
                    return oldCountry?.getId() == newCountry?.getId() && oldCountry?.getName() == newCountry?.getName() &&
                            oldCountry?.getPopulation() == newCountry?.getPopulation()
                }
            })
            countryList = newCountryList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onBindViewHolder(holder: CountryViewHolder?, position: Int) {
        val country = countryList?.get(position)
        holder?.nameView?.text = country?.getName()
        holder?.populationView?.text = country?.getPopulation().toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, position: Int): CountryViewHolder {
        var itemView = LayoutInflater.from(parent?.context).inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return countryList?.size ?: 0
    }

    class CountryViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        val nameView: TextView
        val populationView: TextView

        init {
            nameView = itemView?.findViewById(R.id.name) as TextView
            populationView = itemView?.findViewById(R.id.population) as TextView
        }

    }
}