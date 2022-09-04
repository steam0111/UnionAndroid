package com.itrocket.union.theme.domain.dependencies

interface ColorRepository {

    suspend fun saveColorSettings(
        mainColor: String,
        mainTextColor: String,
        secondaryTextColor: String?,
        appBarBackgroundColor: String?,
        appBarTextColor: String?
    )

    suspend fun initColorSettings()
}