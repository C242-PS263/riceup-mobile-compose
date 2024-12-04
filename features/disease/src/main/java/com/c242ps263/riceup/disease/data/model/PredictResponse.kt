package com.c242ps263.riceup.disease.data.model

import com.google.gson.annotations.SerializedName

data class PredictResponse(
    @SerializedName("prediction")
    val prediction: String,
    @SerializedName("information")
    val information: Information
) {
    data class Information(
        @SerializedName("description")
        val description: String
    )
}