package com.vashisthg.components.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.vashisthg.components.model.TableName.Names.COUNTRIES

/**
 * Created by gauravvashisth on 24/05/17.
 */
@Entity(tableName = COUNTRIES)
class CountryEntity : Country {

    constructor()

    constructor(country: Country) {
        this.id = country.id
        this.name = country.name
        this.population = country.population
    }

    @PrimaryKey
    override var id: Int = 0
        set(value) {}

    override var name: String
        get() = name
        set(value) {}

    override var population: Long = 0
        set(value) {}
}