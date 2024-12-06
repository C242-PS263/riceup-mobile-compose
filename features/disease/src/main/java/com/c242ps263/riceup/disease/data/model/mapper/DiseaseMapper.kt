package com.c242ps263.riceup.disease.data.model.mapper

import com.c242ps263.riceup.disease.data.model.DetectionDisease
import com.c242ps263.riceup.disease.data.model.DetectionResponse
import kotlin.String

object DiseaseMapper {
    fun mapFromResponseToModel(response: DetectionResponse): DetectionDisease {
        return DetectionDisease(
            prediction = response.prediction,
            advice = response.advice,
            file = response.file,
            informationDescription = response.information.description
        )
    }
}