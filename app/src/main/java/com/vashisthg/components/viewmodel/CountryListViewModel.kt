package com.vashisthg.components.viewmodel

import android.app.Application
import android.arch.core.util.Function
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.vashisthg.components.db.DatabaseCreator
import com.vashisthg.components.model.CountryEntity


/**
 * Created by gauravvashisth on 24/05/17.
 */
class CountryListViewModel : AndroidViewModel {

    private var observableProducts: LiveData<List<CountryEntity>>? = null

    constructor(application: Application) : super(application) {
        val databaseCreator: DatabaseCreator? = DatabaseCreator.getInstance(this.getApplication())

        val databaseCreated = databaseCreator?.isDatabaseCreated()

        observableProducts = Transformations.switchMap(databaseCreated,
                object : Function<Boolean, LiveData<List<CountryEntity>>?> {
                    override fun apply(isDbCreated: Boolean?): LiveData<List<CountryEntity>>? {
                        if (java.lang.Boolean.TRUE != isDbCreated) { // Not needed here, but watch out for null
                            return ABSENT
                        } else {
                            return databaseCreator?.getDatabase()?.countryDao()?.loadAllCountried()
                        }
                    }
                })

        databaseCreator?.createDb(this.getApplication<Application>())
    }

    fun getCountries(): LiveData<List<CountryEntity>>? {
        return observableProducts;
    }

    companion object Companion {
        private val ABSENT: MutableLiveData<List<CountryEntity>> = MutableLiveData()
    }
}