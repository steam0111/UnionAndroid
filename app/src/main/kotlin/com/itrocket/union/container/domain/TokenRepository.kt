package com.itrocket.union.container.domain

import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    fun subscribeOnAccessToken(): Flow<String>
}