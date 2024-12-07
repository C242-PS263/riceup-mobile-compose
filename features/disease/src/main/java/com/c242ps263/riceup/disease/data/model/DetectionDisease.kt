package com.c242ps263.riceup.disease.data.model

import com.c242ps263.riceup.disease.data.model.DetectionResponse.Information
import kotlinx.serialization.Serializable

@Serializable
data class DetectionDisease(
    val prediction: String,
    val advice: String,

    var file: String? = null,

    val informationDescription: String,
    val informationName: String,
    val informationImage: String,
    val informationHeadline: String
)