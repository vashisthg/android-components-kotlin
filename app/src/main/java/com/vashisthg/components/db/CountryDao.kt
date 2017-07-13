package com.vashisthg.components.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.vashisthg.components.model.CountryEntity
import com.vashisthg.components.model.TableName


/**
 * Created by gauravvashisth on 24/05/17.
 */
@Dao
interface CountryDao {
    @Query("SELECT * FROM ${TableName.COUNTRIES}")
    fun loadAllCountried(): LiveData<List<CountryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<CountryEntity>)

    @Query("SELECT * FROM ${TableName.COUNTRIES} WHERE id = :p0")
    fun loadCountry(countryId: Int): LiveData<CountryEntity>

    @Query("SELECT * FROM ${TableName.COUNTRIES} WHERE id = :p0")
    fun loadCountrySync(id: Int): CountryEntity

//    @Query("select * from countries where id = :productId")
//    fun loadProductSync(productId: Int): CountryEntity

}