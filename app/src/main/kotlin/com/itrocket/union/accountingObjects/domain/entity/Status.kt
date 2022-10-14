package com.itrocket.union.accountingObjects.domain.entity

import com.itrocket.union.switcher.presentation.store.SwitcherItem

interface Status: SwitcherItem {
    val backgroundColor: String
    override val text: String?
    override val textId: Int?
    val textColor: String
}