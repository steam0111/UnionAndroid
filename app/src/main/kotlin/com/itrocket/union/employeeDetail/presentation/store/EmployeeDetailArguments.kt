package com.itrocket.union.employeeDetail.presentation.store

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmployeeDetailArguments(val employeeId: String) : Parcelable