package com.itrocket.union.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.union.R

@Composable
fun HorizontalFilledIndicator(
    maxCount: Float,
    count: Float,
    modifier: Modifier = Modifier,
    backgroundColor: Color = graphite2
) {
    Box(modifier = modifier.background(backgroundColor, RoundedCornerShape(111.dp))) {
        val fraction = if(maxCount == 0f){
            0f
        } else {
            count / maxCount
        }
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(fraction)
                .background(AppTheme.colors.mainColor, RoundedCornerShape(111.dp))
        )
    }
}

@Composable
fun IndicatorWithText(
    modifier: Modifier = Modifier,
    text: String,
    step: Int,
    stepCount: Int,
    backgroundColor: Color = timberWolf
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = text,
            style = AppTheme.typography.body1
        )
        Spacer(modifier = Modifier.height(11.dp))
        HorizontalFilledIndicator(
            maxCount = stepCount.toFloat(),
            count = step.toFloat(),
            modifier = modifier,
            backgroundColor = backgroundColor
        )
    }
}

@Composable
@Preview
fun IndicatorWithTextPreview() {
    IndicatorWithText(step = 1, stepCount = 3, modifier = Modifier.size(90.dp, 4.dp), text = "Шаг 1")
}

@Composable
@Preview
fun HorizontalFilledIndicatorPreview() {
    HorizontalFilledIndicator(
        55f, 25f,
        Modifier
            .height(8.dp)
            .fillMaxWidth()
    )
}