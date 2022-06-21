package com.itrocket.union.departments.domain.entity

import com.itrocket.union.R
import com.itrocket.union.common.DefaultItem

data class DepartmentDomain(
    val id: String,
    val catalogItemName: String,
    val name: String,
    val code: String,
)

fun DepartmentDomain.toDefaultItem() =
    DefaultItem(
        id = id,
        title = name,
        subtitles = mapOf(R.string.department_code to code)
    )