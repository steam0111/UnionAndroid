package com.itrocket.union.authSelectUser.presentation.store

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthSelectUserResult(val user: String) : Parcelable