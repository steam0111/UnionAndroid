package com.itrocket.union.container.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.authMain.domain.dependencies.AuthMainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MainViewModel(
    private val authMainInteractor: AuthMainInteractor,
) : ViewModel() {

    private val _initialScreen = MutableSharedFlow<InitialScreen>(extraBufferCapacity = 4)
    val initialScreen: Flow<InitialScreen> = _initialScreen

    init {
        getAccessToken()
    }

    private fun getAccessToken() {
        viewModelScope.launch {
            val token = authMainInteractor.subscribeAccessToken().firstOrNull().orEmpty()
            _initialScreen.emit(
                if (token.isBlank()) {
                    InitialScreen.AUTH
                } else {
                    InitialScreen.DOCUMENTS_MENU
                }
            )
        }
    }
}