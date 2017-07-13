package com.vashisthg.components.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.vashisthg.components.model.TableName.Names.COUNTRIES

/**
 * Created by gauravvashisth on 24/05/17.
 */
@Entity(tableName = COUNTRIES)
class CountryEntity : Country {

    @PrimaryKey
    private var id: Int = -1

    private var name: String = ""

    private var population: Long = -1

    override fun getId() = id

    override fun getName() = name

    override fun getPopulation() = population

    fun setId(id: Int) {
        this.id = id
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setPopulation(population: Long) {
        this.population = population
    }
}