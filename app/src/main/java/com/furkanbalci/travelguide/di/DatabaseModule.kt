package com.furkanbalci.travelguide.di

import android.content.Context
import androidx.room.Room
import com.furkanbalci.travelguide.data.datasource.db.TravelDao
import com.furkanbalci.travelguide.data.datasource.db.TravelDatabase
import com.furkanbalci.travelguide.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideTravelDao(travelDatabase: TravelDatabase): TravelDao {
        return travelDatabase.travelDao()
    }

    @Provides
    @Singleton
    fun provideTravelDatabase(@ApplicationContext appContext: Context): TravelDatabase {
        return Room.databaseBuilder(
            appContext,
            TravelDatabase::class.java,
            Constants.ROOM_DB_NAME
        ).allowMainThreadQueries().build()
    }

}