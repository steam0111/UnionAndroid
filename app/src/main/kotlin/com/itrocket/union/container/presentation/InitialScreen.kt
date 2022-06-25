package com.itrocket.union.container.presentation

import com.itrocket.union.R

enum class InitialScreen(val initialScreenId: Int) {
    AUTH(initialScreenId = R.id.auth),
    DOCUMENTS_MENU(initialScreenId = R.id.documentsMenu),
    SYNC_ALL(initialScreenId = R.id.syncAll)
}