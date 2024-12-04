package com.c242ps263.riceup.disease.domain.repository

import com.c242ps263.riceup.disease.data.model.PredictResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import java.io.File

interface DiseaseRepository {
    fun predictApiCall(
        file: MultipartBody.Part
    ): Flow<PredictResponse>
}