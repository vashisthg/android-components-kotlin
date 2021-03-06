package com.vashisthg.components.db

import android.util.Log
import com.vashisthg.components.model.CountryEntity
import java.util.*

/**
 * Created by gauravvashisth on 24/05/17.
 */
class CountryInitUtil {
    private var TAG = "CountryInitUtil"
    private val COUNTRIES = arrayOf("India", "Sweden", "China", "USA", "England", "Canada", "Germany")


    fun initializeDb(db: AppDatabase) {
        val list = generateData()
        insertData(db, list)
    }

    private fun generateData(): List<CountryEntity> {
        val countryList = ArrayList<CountryEntity>(COUNTRIES.size)
        COUNTRIES.indices.forEach { i ->
            val countryEntity = CountryEntity()
            countryEntity.setId(i)
            countryEntity.setName(COUNTRIES[i])
            countryEntity.setPopulation((i * 10000000).toLong())
            countryList.add(countryEntity)
            Log.d(TAG,  "countryEntity: " + countryEntity.toString())

        }
        Log.d(TAG, "size :" + countryList.size)
        return countryList
    }

    private fun insertData(db: AppDatabase, countries: List<CountryEntity>) {
        db.beginTransaction()
        try {
            db.countryDao().insertAll(countries)
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

}