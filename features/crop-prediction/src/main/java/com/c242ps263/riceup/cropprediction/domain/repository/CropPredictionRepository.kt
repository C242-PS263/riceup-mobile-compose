package com.c242ps263.riceup.cropprediction.domain.repository

import com.c242ps263.riceup.cropprediction.data.model.CropPrediction
import com.c242ps263.riceup.disease.data.model.DetectionResponse
import okhttp3.MultipartBody

interface CropPredictionRepository {
    suspend fun predictApiCall(
        body: CropPrediction
    ): CropPredictionResponse
}
