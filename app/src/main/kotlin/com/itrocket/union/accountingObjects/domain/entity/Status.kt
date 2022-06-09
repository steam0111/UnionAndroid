package com.itrocket.union.accountingObjects.domain.entity

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.itrocket.union.ui.white

interface Status{
    val backgroundColor: Color
    val textId: Int
    val textColor: Color
}