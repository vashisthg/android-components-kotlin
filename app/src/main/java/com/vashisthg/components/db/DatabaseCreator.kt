package com.vashisthg.components.db

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.Room
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.vashisthg.components.db.AppDatabase.DATABASE_NAME
import java.util.concurrent.atomic.AtomicBoolean


/**
 * Created by gauravvashisth on 24/05/17.
 */
class DatabaseCreator {

    private val isDatabaseCreated = MutableLiveData<Boolean>()

    private var database: AppDatabase? = null

    private val initializing = AtomicBoolean(true)

    /** Used to observe when the database initialization is done  */
    fun isDatabaseCreated(): LiveData<Boolean> {
        return isDatabaseCreated
    }

    fun getDatabase(): AppDatabase? {
        return database
    }

    fun createDb(context: Context) {

        Log.d("DatabaseCreator", "Creating DB from " + Thread.currentThread().name)

        if (!initializing.compareAndSet(true, false)) {
            return  // Already initializing
        }

        isDatabaseCreated.setValue(false)// Trigger an update to show a loading screen.
        object : AsyncTask<Context, Void, Void>() {

            override fun doInBackground(vararg params: Context): Void? {
                Log.d("DatabaseCreator",
                        "Starting bg job " + Thread.currentThread().name)

                val context = params[0].getApplicationContext()

                // Reset the database to have new data on every run.
                context.deleteDatabase(DATABASE_NAME)

                // Build the database!
                val db = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase::class.java, DATABASE_NAME).build()

                // Add a delay to simulate a long-running operation
                addDelay()

                // Add some data to the database
                CountryInitUtil().initializeDb(db)
                Log.d("DatabaseCreator",
                        "DB was populated in thread " + Thread.currentThread().name)

                database = db
                return null
            }

            override fun onPostExecute(ignored: Void) {
                // Now on the main thread, notify observers that the db is created and ready.
                isDatabaseCreated.setValue(true)
            }
        }.execute(context.getApplicationContext())
    }

    private fun addDelay() {
        try {
            Thread.sleep(4000)
        } catch (ignored: InterruptedException) {
        }

    }

    companion object Companion {
        private var instance: DatabaseCreator? = null

        // For Singleton instantiation
        private val lock = Any()

        @Synchronized fun getInstance(context: Context): DatabaseCreator? {
            if (instance == null) {
                synchronized(lock) {
                    if (instance == null) {
                        instance = DatabaseCreator()
                    }
                }
            }
            return instance
        }
    }
}