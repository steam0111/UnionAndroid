package com.itrocket.union.counterparties.domain.entity

import com.itrocket.union.R
import com.itrocket.union.common.DefaultItem

data class CounterpartyDomain(
    val id: String,
    val catalogItemName: String,
    val name: String,
    val actualAddress: String,
    val legalAddress: String,
    val inn: String,
    val kpp: String,
)

fun CounterpartyDomain.toDefaultItem() =
    DefaultItem(
        id = id,
        title = name,
        subtitles = mapOf(
            R.string.organization_actual_address to actualAddress,
            R.string.organization_legal_address to legalAddress,
            R.string.counterparties_inn to inn,
            R.string.counterparties_kpp to kpp,
        )
    )