package com.vashisthg.components.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.vashisthg.components.model.CountryEntity;

@Database(entities = {CountryEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    static final String DATABASE_NAME = "components-db";

    public abstract CountryDao countryDao();

}