package com.sadri.foursquare.data.persistent

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sadri.foursquare.data.persistent.venue.ExploreDao
import com.sadri.foursquare.data.persistent.venue_detail.VenueDetailDao
import com.sadri.foursquare.models.venue.Venue
import com.sadri.foursquare.models.venue.detail.VenueDetail

/**
 * Created by Sepehr Sadri on 6/3/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@Database(
    entities = [
        Venue::class,
        VenueDetail::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun venueDao(): ExploreDao
    abstract fun venueDetailDao(): VenueDetailDao

    companion object {
        private const val DATABASE_NAME: String = "FOUR_SQUARE"

        @Volatile
        private var DB_INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase {
            if (DB_INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    if (DB_INSTANCE == null) {
                        DB_INSTANCE =
                            Room
                                .databaseBuilder(
                                    context.applicationContext,
                                    AppDatabase::class.java,
                                    DATABASE_NAME
                                )
                                .build()
                    }
                }
            }
            return DB_INSTANCE!!
        }
    }
}