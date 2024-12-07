package com.c242ps263.riceup.disease.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class DetectionResponse(
    @SerializedName("prediction")
    val prediction: String,
    @SerializedName("information")
    val information: Information,
    @SerializedName("advice")
    val advice: String,

    var file: String
) {
    data class Information(
        @SerializedName("name")
        val name: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("headline")
        val headline: String
    )
}