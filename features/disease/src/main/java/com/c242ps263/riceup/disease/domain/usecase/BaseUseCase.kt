package com.c242ps263.riceup.disease.domain.usecase

interface BaseUseCase<in Params, out T> {
    abstract fun execute(params: Params): T
}