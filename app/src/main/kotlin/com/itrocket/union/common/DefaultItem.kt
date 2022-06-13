package com.itrocket.union.common

data class DefaultItem(
    val id: String,
    val title: String,
    val subtitles: Map<String, String?> = mapOf()
)