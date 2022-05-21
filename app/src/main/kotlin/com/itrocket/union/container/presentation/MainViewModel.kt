package com.itrocket.union.container.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.network.NetworkInfo
import com.itrocket.union.serverConnect.domain.ServerConnectInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val authMainInteractor: AuthMainInteractor
) : ViewModel() {

    private val _initialScreen = MutableSharedFlow<InitialScreen>()
    val initialScreen: Flow<InitialScreen> = _initialScreen

    init {
        getAccessToken()
    }

    private fun getAccessToken() {
        viewModelScope.launch {
            try {
                val token = authMainInteractor.getAccessToken()
                _initialScreen.emit(
                    if (token.isBlank()) {
                        InitialScreen.AUTH
                    } else {
                        InitialScreen.DOCUMENTS_MENU
                    }
                )
            } catch (t: Throwable) {
                _initialScreen.emit(InitialScreen.AUTH)
            }
        }
    }
}