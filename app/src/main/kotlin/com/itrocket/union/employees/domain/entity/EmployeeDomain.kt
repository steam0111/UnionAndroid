package com.itrocket.union.employees.domain.entity

import android.os.Parcelable
import androidx.annotation.StringRes
import com.itrocket.union.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmployeeDomain(
    val id: String,
    val catalogItemName: String,
    val firstname: String,
    val lastname: String,
    val patronymic: String,
    val number: String,
    val nfc: String? = null,
    val post: String?,
    val employeeStatus: String? = null
) : Parcelable {
    val hasNfc: Boolean
        get() = nfc != null

    val fullName: String
        get() = "$lastname $firstname $patronymic"
}
