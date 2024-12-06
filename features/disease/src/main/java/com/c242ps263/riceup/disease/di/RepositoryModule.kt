package com.c242ps263.riceup.disease.di

import com.c242ps263.riceup.disease.data.datasource.local.db.DiseaseDatabase
import com.c242ps263.riceup.disease.data.datasource.remote.ApiService
import com.c242ps263.riceup.disease.data.repository.disease.DbDetectionDiseaseRepositoryImpl
import com.c242ps263.riceup.disease.data.repository.disease.DiseaseRepositoryImpl
import com.c242ps263.riceup.disease.domain.repository.DbDetectionDiseaseRepository
import com.c242ps263.riceup.disease.domain.repository.DiseaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDiseaseRepository(apiService: ApiService): DiseaseRepository {
        return DiseaseRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideDbDetectionDiseaseRepository(db: DiseaseDatabase): DbDetectionDiseaseRepository {
        return DbDetectionDiseaseRepositoryImpl(db)
    }
}