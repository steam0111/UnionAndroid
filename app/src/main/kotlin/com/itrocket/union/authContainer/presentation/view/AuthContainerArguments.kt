package com.itrocket.union.authContainer.presentation.view

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthContainerArguments(
    val isShowBackButton: Boolean
) : Parcelable