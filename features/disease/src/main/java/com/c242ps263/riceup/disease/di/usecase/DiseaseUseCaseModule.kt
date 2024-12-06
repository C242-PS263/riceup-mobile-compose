package com.c242ps263.riceup.disease.di.usecase

import com.c242ps263.riceup.disease.domain.repository.DbDetectionDiseaseRepository
import com.c242ps263.riceup.disease.domain.repository.DiseaseRepository
import com.c242ps263.riceup.disease.domain.usecase.disease.GetDiseaseUseCase
import com.c242ps263.riceup.disease.domain.usecase.disease.PredictDiseaseUseCase
import com.c242ps263.riceup.disease.domain.usecase.disease.db.GetDetectionDiseaseDbUseCase
import com.c242ps263.riceup.disease.domain.usecase.disease.db.InsertDetectionDiseaseDbUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DiseaseUseCaseModule {
    @Provides
    fun provideGetDiseaseUseCase(diseaseRepository: DiseaseRepository): GetDiseaseUseCase {
        return GetDiseaseUseCase(diseaseRepository)
    }

    @Provides
    fun providePredictDiseaseUseCase(diseaseRepository: DiseaseRepository): PredictDiseaseUseCase {
        return PredictDiseaseUseCase(diseaseRepository)
    }

    @Provides
    fun provideGetDetectionDiseaseDbUseCase(dbDetectionDiseaseRepository: DbDetectionDiseaseRepository): GetDetectionDiseaseDbUseCase {
        return GetDetectionDiseaseDbUseCase(dbDetectionDiseaseRepository)
    }

    @Provides
    fun provideInsertDetectionDiseaseDbUseCase(dbDetectionDiseaseRepository: DbDetectionDiseaseRepository): InsertDetectionDiseaseDbUseCase {
        return InsertDetectionDiseaseDbUseCase(dbDetectionDiseaseRepository)
    }
}