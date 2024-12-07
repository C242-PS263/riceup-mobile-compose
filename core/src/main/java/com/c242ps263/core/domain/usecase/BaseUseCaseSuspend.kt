package com.c242ps263.core.domain.usecase

interface BaseUseCaseSuspend<in Params, out T> {
    suspend fun execute(params: Params): T
}