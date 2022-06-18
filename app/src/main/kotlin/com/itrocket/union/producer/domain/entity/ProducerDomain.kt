package com.itrocket.union.producer.domain.entity

import com.itrocket.union.R
import com.itrocket.union.common.DefaultItem

data class ProducerDomain(
    val id: String,
    val catalogItemName: String,
    val name: String,
    val code: String,
)

fun ProducerDomain.toDefaultItem() =
    DefaultItem(
        id = id,
        title = name,
        subtitles = mapOf(R.string.producer_code to code)
    )