package com.vashisthg.components

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import com.vashisthg.components.model.CountryEntity
import com.vashisthg.components.viewmodel.CountryListViewModel


class MainActivity : LifecycleActivity() {

    var adapter: CountryAdapter? = null
    var list: RecyclerView? = null
    var mainProgressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        list = findViewById(R.id.main_list) as RecyclerView
        mainProgressBar = findViewById(R.id.main_progressbar) as ProgressBar
        adapter = CountryAdapter()
        list?.adapter = adapter

        val countryListViewModel = ViewModelProviders.of(this).get(CountryListViewModel::class.java)
        subscribeUi(countryListViewModel)
    }

    private fun subscribeUi(viewModel: CountryListViewModel) {
        // Update the list when the data changes
        viewModel.getCountries()?.observe(this, object : Observer<List<CountryEntity>> {
            override fun onChanged(@Nullable myProducts: List<CountryEntity>?) {
                if (myProducts != null) {
                    setIsLoading(false)
                    adapter?.setProductList(myProducts)
                } else {
                    setIsLoading(true)
                }
            }
        })
    }

    private fun setIsLoading(isLoading: Boolean) {
        if (isLoading) {
            mainProgressBar?.visibility = View.VISIBLE
            list?.visibility = View.GONE
        } else {
            mainProgressBar?.visibility = View.GONE
            list?.visibility = View.VISIBLE
        }
    }
}
