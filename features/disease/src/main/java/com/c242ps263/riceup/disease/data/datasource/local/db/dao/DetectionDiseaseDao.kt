package com.c242ps263.riceup.disease.data.datasource.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.c242ps263.riceup.disease.data.datasource.local.db.entity.DetectionDiseaseEntity

@Dao
interface DetectionDiseaseDao {
    @Query("SELECT * FROM detection_diseases")
    fun getDetectionDiseases(): MutableList<DetectionDiseaseEntity>

    @Query("SELECT * FROM detection_diseases WHERE id = :id")
    fun getDetectionDiseaseById(id: String): DetectionDiseaseEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetectionDisease(detectionDisease: DetectionDiseaseEntity): Long

    @Delete
    suspend fun deleteDetectionDisease(detectionDisease: DetectionDiseaseEntity): Int

}