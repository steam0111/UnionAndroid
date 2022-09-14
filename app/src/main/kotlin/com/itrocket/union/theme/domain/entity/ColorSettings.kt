package com.itrocket.union.theme.domain.entity

import androidx.compose.ui.graphics.Color

data class ColorSettings(
    var mainColor: Color = Color(0xFF1269FF),
    var mainTextColor: Color = Color(0xFF253238),
    var secondaryColor: Color = Color(0xFF617E8C),
    var appBarBackgroundColor: Color = Color(0xFF1269FF),
    var appBarTextColor: Color = Color(0xFFFFFFFF),
)