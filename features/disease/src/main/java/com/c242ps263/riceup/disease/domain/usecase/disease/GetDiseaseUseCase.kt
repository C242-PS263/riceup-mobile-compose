package com.c242ps263.riceup.disease.domain.usecase.disease

import com.c242ps263.riceup.disease.data.model.DetectionResponse
import com.c242ps263.riceup.disease.data.model.Disease
import com.c242ps263.riceup.disease.domain.repository.DiseaseRepository
import com.c242ps263.riceup.disease.domain.usecase.BaseUseCaseSuspend
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class GetDiseaseUseCase @Inject constructor(
    private val diseaseRepository: DiseaseRepository
): BaseUseCaseSuspend<Unit, Flow<List<Disease?>>> {
    override suspend fun execute(params: Unit): Flow<List<Disease?>> {
        return flow {
            emit(diseaseRepository.getDiseases())
        }
    }
}