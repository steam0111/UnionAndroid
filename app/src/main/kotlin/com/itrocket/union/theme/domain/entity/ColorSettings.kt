package com.itrocket.union.theme.domain.entity

import androidx.compose.ui.graphics.Color

data class ColorSettings(
    var mainColor: Color = Color(0xFFF26328),
    var mainTextColor: Color = Color(0xFF363636),
    var secondaryColor: Color = Color(0xFF90A4AF),
    var appBarBackgroundColor: Color = Color(0xFF323242),
    var appBarTextColor: Color = Color(0xFFFFFFFF),
)