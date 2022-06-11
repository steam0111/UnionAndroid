package com.itrocket.union.container.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

class OnSessionExpiredUseCase(private val tokenRepository: TokenRepository) {

    fun execute(): Flow<Unit> {
        return subscribeAccessToken().filter {
            it.isBlank()
        }.map {}
    }

    private fun subscribeAccessToken(): Flow<String> {
        return tokenRepository.subscribeOnAccessToken().drop(TOKEN_VALUE_DROP_COUNT)
    }

    companion object {
        // Первый элемент пропускаем, потому что это инитовое значение для показа auth/documentMenu
        private const val TOKEN_VALUE_DROP_COUNT = 1
    }
}