package com.itrocket.union.container.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itrocket.union.container.domain.IsDbSyncedUseCase
import com.itrocket.union.container.domain.IsUserAuthorizedUseCase
import com.itrocket.union.container.domain.OnSessionExpiredUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(
    private val onSessionExpiredUseCase: OnSessionExpiredUseCase,
    private val isUserAuthorizedUseCase: IsUserAuthorizedUseCase,
    private val isDbSyncedUseCase: IsDbSyncedUseCase
) : ViewModel() {

    private val _initialScreen = MutableSharedFlow<InitialScreen>(
        replay = 0,
        extraBufferCapacity = 1,
        BufferOverflow.DROP_OLDEST
    )

    val initialScreen: Flow<InitialScreen> = _initialScreen

    init {
        setInitialScreen()
        subscribeOnSessionExpired()
    }

    private fun setInitialScreen() {
        viewModelScope.launch {
            val isUserAuthorized = isUserAuthorizedUseCase.execute()

            val initialScreen: InitialScreen = when {
                isUserAuthorized -> {
                    val isDbSynced = isDbSyncedUseCase.execute()

                    if (isDbSynced) {
                        InitialScreen.DOCUMENTS_MENU
                    } else {
                        InitialScreen.SYNC_ALL
                    }
                }
                else -> {
                    InitialScreen.AUTH
                }
            }

            _initialScreen.emit(initialScreen)
        }
    }

    private fun subscribeOnSessionExpired() {
        viewModelScope.launch {
            onSessionExpiredUseCase.execute().collect {
                _initialScreen.emit(InitialScreen.AUTH)
            }
        }
    }
}