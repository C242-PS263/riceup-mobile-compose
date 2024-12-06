package com.c242ps263.riceup.disease.domain.repository

import com.c242ps263.riceup.disease.data.model.DetectionResponse
import com.c242ps263.riceup.disease.data.model.Disease
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface DiseaseRepository {
    suspend fun predictApiCall(
        file: MultipartBody.Part
    ): DetectionResponse

    suspend fun getDiseases(): List<Disease?>
}