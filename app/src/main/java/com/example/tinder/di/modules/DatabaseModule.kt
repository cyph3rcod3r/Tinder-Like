package com.example.tinder.di.modules

import com.example.tinder.domain.AppDatabase
import com.example.tinder.domain.dao.PersonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import android.content.Context
import androidx.room.Room

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase = Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "TinderDb"
        ).build()

    @Singleton
    @Provides
    fun provideChannelDao(appDatabase: AppDatabase): PersonDao {
        return appDatabase.personDao()
    }
}