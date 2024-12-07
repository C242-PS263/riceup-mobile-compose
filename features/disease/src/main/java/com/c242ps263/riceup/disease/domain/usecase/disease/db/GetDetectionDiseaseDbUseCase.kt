package com.c242ps263.riceup.disease.domain.usecase.disease.db

import com.c242ps263.riceup.disease.data.datasource.local.db.entity.DetectionDiseaseEntity
import com.c242ps263.riceup.disease.domain.repository.DbDetectionDiseaseRepository
import com.c242ps263.core.domain.usecase.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDetectionDiseaseDbUseCase @Inject constructor(
    private val dbDetectionDiseaseRepository: DbDetectionDiseaseRepository
): BaseUseCase<Unit, Flow<MutableList<DetectionDiseaseEntity>>> {
    override fun execute(params: Unit): Flow<MutableList<DetectionDiseaseEntity>> {
        return dbDetectionDiseaseRepository.getDetectionDiseases()
    }

}