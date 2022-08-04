package com.itrocket.union.filter.domain.entity

import android.os.Parcelable
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import kotlinx.parcelize.Parcelize


sealed class CatalogType : Parcelable {
    @Parcelize
    object AccountingObjects : CatalogType()

    @Parcelize
    object Employees : CatalogType()

    @Parcelize
    object Branches : CatalogType()

    @Parcelize
    object Departments : CatalogType()

    @Parcelize
    object Nomenclatures : CatalogType()

    @Parcelize
    object Regions : CatalogType()

    @Parcelize
    object Reserves : CatalogType()

    @Parcelize
    data class Documents(val documentTypeDomain: DocumentTypeDomain) : CatalogType()

    @Parcelize
    object Default : CatalogType()

    @Parcelize
    object Inventories : CatalogType()
}