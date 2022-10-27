package com.itrocket.union.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itrocket.ui.EditText
import com.itrocket.union.R
import com.itrocket.union.theme.domain.entity.Medias
import com.itrocket.utils.clickableUnbounded

@Composable
fun BaseToolbar(
    title: String = "",
    @DrawableRes startImageId: Int? = null,
    onStartImageClickListener: (() -> Unit)? = null,
    backgroundColor: Color = AppTheme.colors.appBarBackgroundColor,
    textColor: Color = AppTheme.colors.appBarTextColor,
    content: @Composable () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp)
            .background(backgroundColor)
            .padding(vertical = 18.dp, horizontal = 16.dp)
    ) {
        if (startImageId != null) {
            Image(
                painter = painterResource(startImageId),
                contentDescription = null,
                modifier = Modifier.clickableUnbounded(
                    enabled = onStartImageClickListener != null,
                    onClick = onStartImageClickListener ?: { }
                ),
                colorFilter = ColorFilter.tint(AppTheme.colors.mainColor)
            )
        }

        Text(
            text = title,
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f),
            style = AppTheme.typography.body1,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = textColor
        )
        Spacer(modifier = Modifier.width(16.dp))
        content()
    }
}

@Composable
fun SearchToolbar(
    title: String,
    onBackClickListener: () -> Unit,
    onSearchClickListener: (() -> Unit),
    onFilterClickListener: (() -> Unit)? = null,
    onSearchTextChanged: ((String) -> Unit),
    searchText: String,
    isShowSearch: Boolean,
    content: @Composable () -> Unit = {}
) {
    var underlineColor by remember {
        mutableStateOf(graphite2)
    }
    val focusRequest = remember {
        FocusRequester()
    }
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp)
            .background(AppTheme.colors.appBarBackgroundColor)
            .height(56.dp)
            .padding(horizontal = 16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_arrow_back),
            contentDescription = null,
            modifier = Modifier.clickableUnbounded(onClick = onBackClickListener),
            colorFilter = ColorFilter.tint(AppTheme.colors.mainColor)
        )
        Spacer(modifier = Modifier.width(24.dp))
        AnimatedVisibility(
            visible = isShowSearch,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Box(
                contentAlignment = Alignment.BottomStart,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(bottom = 8.dp)
            ) {
                val mainColor = AppTheme.colors.mainColor
                EditText(
                    modifier = Modifier.fillMaxWidth(),
                    text = searchText,
                    hint = stringResource(R.string.search_hint),
                    textStyle = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        letterSpacing = 0.5.sp,
                        color = white
                    ),
                    hintStyle = AppTheme.typography.body2.copy(color = AppTheme.colors.secondaryColor),
                    hintColor = graphite4,
                    onTextChanged = onSearchTextChanged,
                    focusRequester = focusRequest,
                    onFocusChanged = {
                        underlineColor = if (it.hasFocus) {
                            mainColor
                        } else {
                            brightGray
                        }
                    },
                    underlineColor = underlineColor,
                    singleLine = true,
                    cursorBrush = SolidColor(white),
                    linePadding = 8.dp
                )
            }
            LaunchedEffect(Unit) {
                focusRequest.requestFocus()
            }
        }
        AnimatedVisibility(
            visible = !isShowSearch,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    style = AppTheme.typography.body1,
                    fontWeight = FontWeight.Medium,
                    color = AppTheme.colors.appBarTextColor
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(R.drawable.ic_search_white),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(AppTheme.colors.mainColor),
                    modifier = Modifier
                        .clickableUnbounded(onClick = onSearchClickListener)
                )
                if (onFilterClickListener != null) {
                    Spacer(modifier = Modifier.width(28.dp))
                    Image(
                        painter = painterResource(R.drawable.ic_filter_white),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(AppTheme.colors.mainColor),
                        modifier = Modifier.clickableUnbounded(onClick = onFilterClickListener)
                    )
                }
                content()
            }
        }
    }
}

@Composable
fun LogoToolbar(medias: Medias?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            medias?.logo != null -> {
                Image(bitmap = medias.logo, contentDescription = null)
            }
            medias != null -> {
                Image(
                    painter = painterResource(R.drawable.ic_logo),
                    contentDescription = null,
                )
            }

        }
    }
}
