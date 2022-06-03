package com.itrocket.union.container.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.container.domain.IsUserAuthorizedUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch

class MainViewModel(
    private val authMainInteractor: AuthMainInteractor,
    private val isUserAuthorizedUseCase: IsUserAuthorizedUseCase,
) : ViewModel() {

    private val _initialScreen = MutableSharedFlow<InitialScreen>(extraBufferCapacity = 4)
    val initialScreen: Flow<InitialScreen> = _initialScreen

    init {
        getAccessToken()
        listenLogout()
    }

    private fun getAccessToken() {
        viewModelScope.launch {
            val isUserAuthorized = isUserAuthorizedUseCase.execute()

            _initialScreen.emit(
                if (isUserAuthorized) {
                    InitialScreen.DOCUMENTS_MENU
                } else {
                    InitialScreen.AUTH
                }
            )
        }
    }

    private fun listenLogout() {
        viewModelScope.launch {
            authMainInteractor.checkAccessTokenExpired().collect {
                if (it.isBlank()) {
                    _initialScreen.emit(InitialScreen.AUTH)
                }
            }
        }
    }

}