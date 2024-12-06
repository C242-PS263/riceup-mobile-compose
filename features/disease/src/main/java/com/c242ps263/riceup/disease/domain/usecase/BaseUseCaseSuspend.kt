package com.c242ps263.riceup.disease.domain.usecase

interface BaseUseCaseSuspend<in Params, out T> {
    suspend fun execute(params: Params): T
}