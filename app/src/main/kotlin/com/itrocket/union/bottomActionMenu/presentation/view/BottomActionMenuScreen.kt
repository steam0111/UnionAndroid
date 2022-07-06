package com.itrocket.union.bottomActionMenu.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.core.base.AppInsets
import com.itrocket.union.R
import com.itrocket.union.bottomActionMenu.presentation.store.BottomActionMenuStore
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.ui.*

@Composable
fun BottomActionMenuScreen(
    state: BottomActionMenuStore.State,
    appInsets: AppInsets,
    onCreateDocClickListener: (ReservesDomain) -> Unit,
    onOpenItemClickListener: (ReservesDomain) -> Unit,
    onDeleteItemClickListener: (ReservesDomain) -> Unit
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
                Spacer(modifier = Modifier.height(16.dp))
                BottomMenuButtons(
                    onCreateDocClickListener = {
                        onCreateDocClickListener
                    },
                    onOpenItemClickListener = { onOpenItemClickListener },
                    onDeleteItemClickListener = { onDeleteItemClickListener }
                )
                Spacer(modifier = Modifier.height(16.dp))
                BottomBar {

                }
            }
        }
    }
}
@Composable
private fun BottomBar(
    onReadingModeClickListener: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(graphite2)
            .padding(16.dp)
    ) {
        BaseButton(
            text = stringResource(R.string.accounting_object_detail_reading_mode),
            onClick = onReadingModeClickListener,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun BottomMenuButtons(
    onCreateDocClickListener: () -> Unit,
    onOpenItemClickListener: () -> Unit,
    onDeleteItemClickListener: () -> Unit
) {
    Column {
        BottomBarIdentifyItem(
            isEnabled = true,
            text = "Создать документ",
            onClick = onCreateDocClickListener
        )
        BottomBarIdentifyItem(
            onClick = onOpenItemClickListener,
            isEnabled = true,
            text = "Открыть карточку"
        )
        BottomBarIdentifyItem(
            onClick = onDeleteItemClickListener,
            isEnabled = true,
            text = "Удалить из списка"
        )
    }
}

@Composable
fun BottomBarIdentifyItem(
    text: String,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(graphite2)
            .padding(8.dp)
    ) {
        ButtonWithLoaderPsb1(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            isEnabled = isEnabled,

            ) {
            Text(
                text = text,
                style = AppTheme.typography.body2,
                color = white,
                fontWeight = FontWeight.Medium
            )
        }
    }
}


@Composable
@Preview
private fun BottomBarIdentifyPreview() {
    Column {
        BottomBarIdentifyItem(
            onClick = {/*TODO*/ },
            isEnabled = true,
            text = "Создать документ"
        )
        BottomBarIdentifyItem(
            onClick = {/*TODO*/ },
            isEnabled = true,
            text = "Открыть карточку"
        )
        BottomBarIdentifyItem(
            onClick = {/*TODO*/ },
            isEnabled = true,
            text = "Удалить из списка"
        )
    }
}