package com.itrocket.union.authMain.domain.entity

data class MyConfigDomain(
    val employeeId: String?,
    val isSuperUser: Boolean = false,
    val permissions: List<MyConfigPermission>?
)