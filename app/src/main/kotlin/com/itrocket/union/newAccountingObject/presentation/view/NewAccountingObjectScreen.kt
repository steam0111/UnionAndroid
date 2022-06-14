package com.itrocket.union.newAccountingObject.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.union.R
import com.itrocket.union.ui.AppTheme
import com.itrocket.core.base.AppInsets
import com.itrocket.union.newAccountingObject.presentation.store.NewAccountingObjectStore
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.ui.BottomLine
import com.itrocket.union.ui.ExpandedInfoField
import com.itrocket.union.ui.TextButton
import com.itrocket.union.ui.graphite4
import com.itrocket.union.ui.psb4
import com.itrocket.union.ui.white
import com.itrocket.utils.clickableUnbounded
import com.itrocket.utils.rememberViewInteropNestedScrollConnection

@Composable
fun NewAccountingObjectScreen(
    state: NewAccountingObjectStore.State,
    appInsets: AppInsets,
    onCrossClickListener: () -> Unit
) {
    AppTheme {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = appInsets.bottomInset.dp, top = appInsets.topInset.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(white, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .padding(top = 16.dp),
                ) {
                    Content(
                        accountingObject = state.accountingObject,
                        onCrossClickListener = onCrossClickListener
                    )
                }
        }
    }
}

@Composable
private fun Content(accountingObject: AccountingObjectDomain, onCrossClickListener: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.new_accounting_object_title),
            style = AppTheme.typography.h6
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.ic_cross),
            contentDescription = null,
            colorFilter = ColorFilter.tint(
                graphite4
            ),
            modifier = Modifier.clickableUnbounded(onClick = onCrossClickListener)
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
    Spacer(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(psb4)
    )
    Text(
        text = accountingObject.title,
        style = AppTheme.typography.body1,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(16.dp)
    )
    Surface(
        // Добавляет возможность скроллить внутренний компонент без скролла внешнего.
        // Когда скролл внутреннего компонента дошел до конца,
        // начинается скролл внешнего компонента
        modifier = Modifier.nestedScroll(rememberViewInteropNestedScrollConnection())
    ) {
        LazyColumn {
            item {
                BottomLine()
            }
            items(accountingObject.listMainInfo) {
                ExpandedInfoField(
                    label = it.title,
                    value = it.value,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview(
    name = "светлая тема экран - 6.3 (3040x1440)",
    showSystemUi = true,
    device = Devices.PIXEL_4_XL,
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    name = "темная тема экран - 4,95 (1920 × 1080)",
    showSystemUi = true,
    device = Devices.NEXUS_5,
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(name = "планшет", showSystemUi = true, device = Devices.PIXEL_C)
@Composable
fun NewAccountingObjectScreenPreview() {
    NewAccountingObjectScreen(NewAccountingObjectStore.State(
        accountingObject = AccountingObjectDomain(
            id = "8",
            isBarcode = true,
            title = "Ширикоформатный жидкокристалический монитор Samsung ЕК288, 23 дюйма",
            status = ObjectStatus.AVAILABLE,
            listMainInfo = listOf(
                ObjectInfoDomain(
                    "Заводской номер",
                    "AV169V100E00442"
                ),
                ObjectInfoDomain(
                    "Инвентарный номер",
                    "6134509345098749"
                ),
            ),
            listAdditionallyInfo = listOf()
        )
    ), AppInsets(topInset = previewTopInsetDp), {})
}