package com.itrocket.union.selectActionWithValuesBottomMenu.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.core.base.AppInsets
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatusType
import com.itrocket.union.documents.domain.entity.ActionsWithIdentifyObjects
import com.itrocket.union.selectActionWithValuesBottomMenu.presentation.store.SelectActionWithValuesBottomMenuStore
import com.itrocket.union.ui.*

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

@Preview
@Composable
fun SelectActionWithValuesBottomMenuScreenPreview() {
    SelectActionWithValuesBottomMenuScreen(
        state = SelectActionWithValuesBottomMenuStore.State(
            actionsWithIdentifyObjects = listOf(
                ActionsWithIdentifyObjects.OPEN_CARD,
                ActionsWithIdentifyObjects.DELETE_FROM_LIST
            ),
            accountingObject = AccountingObjectDomain(
                id = "1",
                isBarcode = true,
                title = "Ширикоформатный жидкокристалический монитор Samsung",
                status = ObjectStatus("AVAILABLE", ObjectStatusType.AVAILABLE),
                listMainInfo = listOf(),
                listAdditionallyInfo = listOf(),
                barcodeValue = "",
                rfidValue = ""
            ),
            accountingObjects = listOf()
        ), AppInsets()
    ) {}
}