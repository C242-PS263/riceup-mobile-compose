package com.c242ps263.riceup.disease.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Disease(
    val id: String,
    val name: String,
    val headline: String,
    val description: String,
    val image: String,
    val locale: String
)