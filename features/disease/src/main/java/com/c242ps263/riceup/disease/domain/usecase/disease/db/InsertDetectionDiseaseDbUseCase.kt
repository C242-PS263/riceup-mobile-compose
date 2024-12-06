package com.c242ps263.riceup.disease.domain.usecase.disease.db

import com.c242ps263.riceup.disease.data.datasource.local.db.entity.DetectionDiseaseEntity
import com.c242ps263.riceup.disease.domain.repository.DbDetectionDiseaseRepository
import com.c242ps263.riceup.disease.domain.usecase.BaseUseCaseSuspend
import javax.inject.Inject

class InsertDetectionDiseaseDbUseCase @Inject constructor(
    private val dbDetectionDiseaseRepository: DbDetectionDiseaseRepository
): BaseUseCaseSuspend<DetectionDiseaseEntity, Long> {
    override suspend fun execute(params: DetectionDiseaseEntity): Long {
        return dbDetectionDiseaseRepository.insertDetectionDisease(params)
    }

}