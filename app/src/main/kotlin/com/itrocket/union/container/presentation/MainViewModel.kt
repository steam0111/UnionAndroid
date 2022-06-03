package com.itrocket.union.container.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itrocket.union.container.domain.IsUserAuthorizedUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val isUserAuthorizedUseCase: IsUserAuthorizedUseCase
) : ViewModel() {

    private val _initialScreen = MutableSharedFlow<InitialScreen>(extraBufferCapacity = 4)
    val initialScreen: Flow<InitialScreen> = _initialScreen

    init {
        getAccessToken()
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
}