package com.itrocket.union.accountingObjects.domain.entity

import androidx.compose.ui.graphics.Color
import com.itrocket.union.switcher.presentation.store.SwitcherItem

interface Status: SwitcherItem {
    val backgroundColor: Color
    override val text: String?
    override val textId: Int?
    val textColor: Color
}