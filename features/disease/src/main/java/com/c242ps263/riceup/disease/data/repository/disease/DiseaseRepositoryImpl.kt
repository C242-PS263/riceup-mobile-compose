package com.c242ps263.riceup.disease.data.repository.disease

import com.c242ps263.riceup.disease.data.datasource.remote.ApiService
import com.c242ps263.riceup.disease.data.model.DetectionResponse
import com.c242ps263.riceup.disease.domain.repository.DiseaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiseaseRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): DiseaseRepository {
    override suspend fun predictApiCall(file: MultipartBody.Part): DetectionResponse {
        return apiService.predict(file)
    }

}