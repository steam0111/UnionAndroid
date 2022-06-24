package com.itrocket.union.nomenclatureDetail.presentation.store

import android.os.Parcelable
import com.itrocket.union.employees.domain.entity.EmployeeDomain
import com.itrocket.union.nomenclature.domain.entity.NomenclatureDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class NomenclatureDetailArguments(val id: String) : Parcelable