package com.c242ps263.riceup.disease.data.repository.disease

import com.c242ps263.riceup.disease.data.datasource.local.db.DiseaseDatabase
import com.c242ps263.riceup.disease.data.datasource.local.db.entity.DetectionDiseaseEntity
import com.c242ps263.riceup.disease.domain.repository.DbDetectionDiseaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DbDetectionDiseaseRepositoryImpl @Inject constructor(
    private val db: DiseaseDatabase
): DbDetectionDiseaseRepository {
    override fun getDetectionDiseases(): Flow<MutableList<DetectionDiseaseEntity>> {
        return flowOf(db.detectionDiseaseDao().getDetectionDiseases())
    }

    override fun getDetectionDiseaseById(id: String): Flow<DetectionDiseaseEntity> {
        return flowOf(db.detectionDiseaseDao().getDetectionDiseaseById(id))
    }

    override suspend fun insertDetectionDisease(detectionDisease: DetectionDiseaseEntity): Long {
        return db.detectionDiseaseDao().insertDetectionDisease(detectionDisease)
    }

    override suspend fun deleteDetectionDisease(detectionDisease: DetectionDiseaseEntity): Int {
        return db.detectionDiseaseDao().deleteDetectionDisease(detectionDisease)
    }

}