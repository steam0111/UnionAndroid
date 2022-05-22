package com.itrocket.union.authContainer.presentation.view

interface AuthContainer {

    fun isButtonEnable(enabled: Boolean)

    fun onNextFinished()

    fun isLoading(isLoading: Boolean)
}