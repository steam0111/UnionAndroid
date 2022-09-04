package com.itrocket.union.switcher.presentation.store

import java.io.Serializable

interface SwitcherItem : Serializable {
    val text: String?
    val textId: Int?
}