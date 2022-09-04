package com.itrocket.union.container.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itrocket.union.container.domain.OnSessionExpiredUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(
    private val onSessionExpiredUseCase: OnSessionExpiredUseCase,
) : ViewModel() {

    private val _initialScreen = MutableSharedFlow<InitialScreen>(
        replay = 0,
        extraBufferCapacity = 1,
        BufferOverflow.DROP_OLDEST
    )

    val initialScreen: Flow<InitialScreen> = _initialScreen

    init {
        subscribeOnSessionExpired()
    }

    private fun subscribeOnSessionExpired() {
        viewModelScope.launch {
            onSessionExpiredUseCase.execute().collect {
                _initialScreen.emit(InitialScreen.AUTH)
            }
        }
    }
}