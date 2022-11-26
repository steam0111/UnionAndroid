package com.itrocket.union.selectParams.presentation.store

import android.os.Parcelable
import com.itrocket.union.filter.domain.entity.CatalogType
import com.itrocket.union.manual.ParamDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectParamsArguments(
    val currentFilter: ParamDomain,
    val allParams: List<ParamDomain>,
    val sourceScreen: CatalogType? = null
) : Parcelable