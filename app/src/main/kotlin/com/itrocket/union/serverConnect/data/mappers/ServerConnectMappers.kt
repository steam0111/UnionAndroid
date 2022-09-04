package com.itrocket.union.serverConnect.data.mappers

import com.itrocket.union.serverConnect.domain.entity.ColorDomain
import org.openapitools.client.models.StyleSettingsDtoV2

fun StyleSettingsDtoV2.toDomain() = ColorDomain(
    mainColor = mainColor,
    mainTextColor = mainTextColor,
    secondaryTextColor = secondaryTextColor,
    appBarTextColor = appBarTextColor,
    appBarBackgroundColor = appBarBackgroundColor
)