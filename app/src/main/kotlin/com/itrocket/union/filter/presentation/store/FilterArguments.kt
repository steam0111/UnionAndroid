package com.itrocket.union.filter.presentation.store

import android.os.Parcelable
import com.itrocket.union.filter.domain.entity.CatalogType
import com.itrocket.union.manual.ParamDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterArguments(val argument: List<ParamDomain>, val from: CatalogType) : Parcelable