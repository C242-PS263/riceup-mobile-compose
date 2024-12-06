package com.c242ps263.riceup.disease.di

import android.content.Context
import androidx.room.Room
import com.c242ps263.riceup.disease.data.datasource.local.db.DiseaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDiseaseDatabase(@ApplicationContext context: Context): DiseaseDatabase {
        return Room.databaseBuilder(context, DiseaseDatabase::class.java, "disease.db").build()
    }
}