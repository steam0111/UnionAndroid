package com.itrocket.union.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain

private const val MAX_LIST_INFO = 3

@Composable
fun AccountingObjectItem(
    accountingObject: AccountingObjectDomain,
    onAccountingObjectListener: (AccountingObjectDomain) -> Unit,
    isShowBottomLine: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                onAccountingObjectListener(accountingObject)
            })
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.7f)
        ) {
            Text(
                text = accountingObject.title,
                style = AppTheme.typography.body1,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            accountingObject.listMainInfo.take(MAX_LIST_INFO).forEach {
                Text(
                    text = stringResource(R.string.common_two_dots, it.title, it.value),
                    style = AppTheme.typography.caption,
                    color = psb3
                )
            }
        }
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
            SmallStatusLabel(objectStatus = accountingObject.status)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = stringResource(R.string.common_barcode),
                    style = AppTheme.typography.caption,
                    color = graphite5,
                    modifier = Modifier.padding(end = 4.dp)
                )
                RadioButton(
                    selected = accountingObject.isBarcode,
                    onClick = null,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = psb6,
                        unselectedColor = graphite3
                    )
                )
            }
            if (accountingObject.status == ObjectStatus.AVAILABLE) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = stringResource(R.string.common_rfid),
                        style = AppTheme.typography.caption,
                        color = graphite5,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    RadioButton(
                        selected = accountingObject.isBarcode,
                        onClick = null,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = psb6,
                            unselectedColor = graphite3
                        )
                    )
                }
            }
        }
    }
    if (isShowBottomLine) {
        BottomLine()
    }
}

@Composable
fun ReservesItem(
    reserves: ReservesDomain,
    onReservesListener: (ReservesDomain) -> Unit,
    isShowBottomLine: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                onReservesListener(reserves)
            })
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.7f)
        ) {
            Text(
                text = reserves.title,
                style = AppTheme.typography.body1,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            reserves.listInfo.take(MAX_LIST_INFO).forEach {
                Text(
                    text = stringResource(R.string.common_two_dots, it.title, it.value),
                    style = AppTheme.typography.caption,
                    color = psb3
                )
            }
        }
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
            Text(
                text = reserves.itemsCount.toString(),
                style = AppTheme.typography.body2,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = stringResource(R.string.common_barcode),
                    style = AppTheme.typography.caption,
                    color = graphite5,
                    modifier = Modifier.padding(end = 4.dp)
                )
                RadioButton(
                    selected = reserves.isBarcode,
                    onClick = null,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = psb6,
                        unselectedColor = graphite3
                    )
                )
            }
        }
    }
    if (isShowBottomLine) {
        BottomLine()
    }
}

@Preview
@Composable
fun ReservesItemPreview() {
    ReservesItem(
        reserves = ReservesDomain(
            id = "1", title = "Авторучка «Зебра TR22»", isBarcode = true, listInfo =
            listOf(
                ObjectInfoDomain(
                    "Заводской номер",
                    "таылватвлыавыалвыоалвыа"
                ),
                ObjectInfoDomain(
                    "Инвентарный номер",
                    "таылватвлыавыалвыоалвыа"
                )
            ), itemsCount = 1200
        ), onReservesListener = {},
        isShowBottomLine = true
    )
}

@Preview
@Composable
fun AccountingObjectItemPreview() {
    AccountingObjectItem(
        accountingObject = AccountingObjectDomain(
            id = "1",
            isBarcode = true,
            title = "Ширикоформатный жидкокристалический монитор Samsung",
            status = ObjectStatus.UNDER_REVIEW,
            listMainInfo = listOf(
                ObjectInfoDomain(
                    "Заводской номер",
                    "таылватвлыавыалвыоалвыа"
                ),
                ObjectInfoDomain(
                    "Инвентарный номер",
                    "таылватвлыавыалвыоалвыа"
                ),
            ),
            listAdditionallyInfo = listOf(
                ObjectInfoDomain(
                    "Заводской номер",
                    "таылватвлыавыалвыоалвыа"
                ),
                ObjectInfoDomain(
                    "Инвентарный номер",
                    "таылватвлыавыалвыоалвыа"
                ),
            )
        ), onAccountingObjectListener = {}, isShowBottomLine = true
    )
}