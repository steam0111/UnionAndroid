package com.itrocket.union.authMain.data

import com.itrocket.union.authMain.data.mapper.toAuthRequest
import com.itrocket.union.authMain.domain.dependencies.AuthMainRepository
import com.itrocket.union.authMain.domain.entity.AuthCredsDomain
import com.itrocket.union.network.AuthApi

class AuthMainRepositoryImpl(private val api: AuthApi) : AuthMainRepository {
    override suspend fun signIn(authCreds: AuthCredsDomain): AuthResponse? {
        return api.signIn(authCreds.toAuthRequest())
    }

}