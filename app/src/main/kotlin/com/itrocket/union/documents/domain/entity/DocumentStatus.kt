package com.itrocket.union.documents.domain.entity

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.itrocket.union.R
import com.itrocket.union.ui.graphite2
import com.itrocket.union.ui.graphite6
import com.itrocket.union.ui.green7
import com.itrocket.union.ui.white

enum class DocumentStatus(
    val backgroundColor: Color,
    @StringRes val textId: Int,
    val textColor: Color
) {
    CREATED(
        textId = R.string.documents_created,
        backgroundColor = graphite2,
        textColor = graphite6
    ),
    CONDUCTED(textId = R.string.documents_conducted,
        backgroundColor = green7,
        textColor = white
    )
}