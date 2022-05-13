package com.itrocket.union.authMain.presentation.store

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AuthMainArguments(val login: String, val password: String) : Parcelable