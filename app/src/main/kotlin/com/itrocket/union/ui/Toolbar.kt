package com.itrocket.union.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.itrocket.utils.clickableUnbounded
import com.itrocket.union.R


@Composable
fun BaseToolbar(
    title: String,
    @DrawableRes startImageId: Int,
    onStartImageClickListener: (() -> Unit)? = null,
    backgroundColor: Color = white,
    textColor: Color = psb1,
    content: @Composable () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp)
            .background(backgroundColor)
            .padding(vertical = 20.dp, horizontal = 16.dp)
    ) {
        Image(
            painter = painterResource(startImageId),
            contentDescription = null,
            modifier = Modifier.clickableUnbounded(
                enabled = onStartImageClickListener != null,
                onClick = onStartImageClickListener ?: { }
            )
        )
        Text(
            text = title,
            modifier = Modifier.padding(start = 16.dp),
            style = AppTheme.typography.h6,
            color = textColor
        )
        Spacer(modifier = Modifier.weight(1f))
        content()
    }
}

@Composable
fun BlackToolbar(
    title: String,
    onBackClickListener: () -> Unit,
    onSearchClickListener: () -> Unit,
    onFilterClickListener: () -> Unit
) {
    BaseToolbar(
        title = title,
        startImageId = R.drawable.ic_arrow_back,
        onStartImageClickListener = onBackClickListener,
        backgroundColor = psb1,
        textColor = white
    ) {
        Image(
            painter = painterResource(R.drawable.ic_search_white),
            contentDescription = null,
            modifier = Modifier
                .clickableUnbounded(onClick = onSearchClickListener)
        )
        Spacer(modifier = Modifier.width(28.dp))
        Image(
            painter = painterResource(R.drawable.ic_filter_white),
            contentDescription = null,
            modifier = Modifier.clickableUnbounded(onClick = onFilterClickListener)
        )
    }
}
