package com.c242ps263.riceup.cropprediction.data.datasource.remote

import com.c242ps263.riceup.cropprediction.data.model.CropPrediction
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @POST("predict-crop-yield")
    suspend fun predict(
        @Body cropPrediction: CropPrediction
    ): CropPrediction
}