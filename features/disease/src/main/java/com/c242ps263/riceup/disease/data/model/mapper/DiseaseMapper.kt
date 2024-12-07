package com.c242ps263.riceup.disease.data.model.mapper

import com.c242ps263.riceup.disease.data.datasource.local.db.entity.DetectionDiseaseEntity
import com.c242ps263.riceup.disease.data.model.DetectionDisease
import com.c242ps263.riceup.disease.data.model.DetectionResponse
import kotlin.String

object DiseaseMapper {
    fun mapFromResponseToModel(response: DetectionResponse): DetectionDisease {
        return DetectionDisease(
            prediction = response.prediction,
            advice = response.advice,
            file = response.file,
            informationDescription = response.information.description,
            informationName = response.information.name,
            informationImage = response.information.image,
            informationHeadline = response.information.headline
        )
    }

    fun mapFromDetectionToEntity(detectionDisease: DetectionDisease, id: String): DetectionDiseaseEntity {
        return DetectionDiseaseEntity(
            prediction = detectionDisease.prediction,
            advice = detectionDisease.advice,
            file = detectionDisease.file,
            id = id,
            informationDescription = detectionDisease.informationDescription,
            informationName = detectionDisease.informationName,
            informationImage = detectionDisease.informationImage,
            informationHeadline = detectionDisease.informationHeadline
        )
    }
}