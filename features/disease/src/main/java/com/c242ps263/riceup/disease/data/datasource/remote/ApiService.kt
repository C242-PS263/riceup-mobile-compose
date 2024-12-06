package com.c242ps263.riceup.disease.data.datasource.remote

import com.c242ps263.riceup.disease.data.model.DetectionResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @POST("predict")
    @Multipart
    suspend fun predict(
        @Part image: MultipartBody.Part
    ): DetectionResponse
}