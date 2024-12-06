package com.c242ps263.riceup.disease.data.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.c242ps263.riceup.disease.data.datasource.local.db.dao.DetectionDiseaseDao
import com.c242ps263.riceup.disease.data.datasource.local.db.entity.DetectionDiseaseEntity

@Database(
    entities = [DetectionDiseaseEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DiseaseDatabase: RoomDatabase() {
    abstract fun detectionDiseaseDao(): DetectionDiseaseDao
}