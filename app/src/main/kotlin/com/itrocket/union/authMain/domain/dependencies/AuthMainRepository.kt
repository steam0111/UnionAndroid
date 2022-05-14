package com.itrocket.union.authMain.domain.dependencies

import com.itrocket.union.authMain.data.AuthResponse
import com.itrocket.union.authMain.domain.entity.AuthCredsDomain

interface AuthMainRepository {

    suspend fun signIn(authCreds: AuthCredsDomain): AuthResponse?
}