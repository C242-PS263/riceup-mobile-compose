package com.c242ps263.riceup.disease.domain.usecase.disease

import com.c242ps263.riceup.disease.data.model.DetectionResponse
import com.c242ps263.riceup.disease.domain.repository.DiseaseRepository
import com.c242ps263.core.domain.usecase.BaseUseCaseSuspend
import okhttp3.MultipartBody
import javax.inject.Inject

class PredictDiseaseUseCase @Inject constructor(
    private val diseaseRepository: DiseaseRepository
): BaseUseCaseSuspend<MultipartBody.Part, DetectionResponse> {
    override suspend fun execute(params: MultipartBody.Part): DetectionResponse {
        return diseaseRepository.predictApiCall(params)
    }
}