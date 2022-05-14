package com.itrocket.union.authMain.domain

import android.util.Log
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.authMain.domain.dependencies.AuthMainRepository
import com.itrocket.union.authMain.domain.entity.AuthCredsDomain
import com.itrocket.union.core.Prefs
import kotlinx.coroutines.withContext

class AuthMainInteractor(
    private val repository: AuthMainRepository,
    private val coreDispatchers: CoreDispatchers,
    private val prefs: Prefs
) {

    suspend fun signIn(login: String, password: String) {
        withContext(coreDispatchers.io) {
            val response = repository.signIn(AuthCredsDomain(login = login, password = password))
            Log.e("AuthSuccess", response.toString())
            prefs.accessToken = response?.accessToken
            prefs.refreshToken = response?.refreshToken
        }
    }
}