package com.c242ps263.riceup.disease.data.datasource.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "detection_diseases", primaryKeys = ["id"])
data class DetectionDiseaseEntity(
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "prediction")
    val prediction: String,
    @ColumnInfo(name = "information_description")
    val informationDescription: String,
    @ColumnInfo(name = "advice")
    val advice: String,
    @ColumnInfo(name = "file")
    val file: String? = null,
)