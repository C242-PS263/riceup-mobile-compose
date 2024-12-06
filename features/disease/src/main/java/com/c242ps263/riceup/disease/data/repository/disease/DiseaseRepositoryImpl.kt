package com.c242ps263.riceup.disease.data.repository.disease

import com.c242ps263.riceup.disease.data.datasource.remote.ApiService
import com.c242ps263.riceup.disease.data.model.DetectionResponse
import com.c242ps263.riceup.disease.data.model.Disease
import com.c242ps263.riceup.disease.domain.repository.DiseaseRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
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

    override suspend fun getDiseases(): List<Disease?> {
        return Firebase.firestore.collection("diseases")
            .get()
            .await()
            .map {
                Disease(
                    id = it.getString("id") ?: "",
                    name = it.getString("name") ?: "",
                    headline = it.getString("headline") ?: "",
                    description = it.getString("description") ?: "",
                    image = it.getString("image") ?: "",
                    locale = it.getString("locale") ?: ""
                )
            }
    }
}