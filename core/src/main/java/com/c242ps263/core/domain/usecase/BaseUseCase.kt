package com.c242ps263.core.domain.usecase

interface BaseUseCase<in Params, out T> {
    abstract fun execute(params: Params): T
}