package com.c242ps263.riceup.cropprediction.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CropPrediction(
    @SerializedName("land_area")
    val landArea: Int,
    @SerializedName("rainfall")
    val rainfall: String,
    @SerializedName("disease_level")
    val diseaseLevel: String,
    @SerializedName("temperature")
    val temperature: Double,
    @SerializedName("planting_distance")
    val plantingDistance: String
)