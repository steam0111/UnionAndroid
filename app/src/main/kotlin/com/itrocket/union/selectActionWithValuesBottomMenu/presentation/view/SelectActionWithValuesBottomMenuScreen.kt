package com.itrocket.union.selectActionWithValuesBottomMenu.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itrocket.core.base.AppInsets
import com.itrocket.union.R
import com.itrocket.union.documents.domain.entity.ActionsWithIdentifyObjects
import com.itrocket.union.selectActionWithValuesBottomMenu.presentation.store.SelectActionWithValuesBottomMenuStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BaseButton
import com.itrocket.union.ui.BottomSheetDivider
import com.itrocket.union.ui.graphite2
import com.itrocket.union.ui.psb1
import com.itrocket.union.ui.white

@Composable
fun SelectActionWithValuesBottomMenuScreen(
    state: SelectActionWithValuesBottomMenuStore.State,
    appInsets: AppInsets,
    onTypeClickListener: (ActionsWithIdentifyObjects) -> Unit
) {
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = appInsets.bottomInset.dp,
                    top = appInsets.topInset.dp
                ),
            contentAlignment = Alignment.BottomStart
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(white, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .padding(
                        top = 8.dp,
                        start = 16.dp,
                        end = 16.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BottomSheetDivider()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(id = R.string.choose_action),
                    style = AppTheme.typography.h6,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                state.actionsWithIdentifyObjects.forEach {
                    Spacer(modifier = Modifier.height(16.dp))
                    BaseButton(
                        text = stringResource(id = it.textId),
                        onClick = { onTypeClickListener(it) },
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = psb1
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}