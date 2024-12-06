package com.c242ps263.riceup.disease.domain.repository

import com.c242ps263.riceup.disease.data.datasource.local.db.entity.DetectionDiseaseEntity
import kotlinx.coroutines.flow.Flow

interface DbDetectionDiseaseRepository {
    fun getDetectionDiseases(): Flow<MutableList<DetectionDiseaseEntity>>
    fun getDetectionDiseaseById(id: String): Flow<DetectionDiseaseEntity>
    suspend fun insertDetectionDisease(detectionDisease: DetectionDiseaseEntity): Long
    suspend fun deleteDetectionDisease(detectionDisease: DetectionDiseaseEntity): Int
}