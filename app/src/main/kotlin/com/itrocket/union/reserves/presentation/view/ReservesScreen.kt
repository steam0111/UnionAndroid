package com.itrocket.union.reserves.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.union.R
import com.itrocket.union.ui.AppTheme
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.reserves.presentation.store.ReservesStore
import com.itrocket.union.ui.AccountingObjectItem
import com.itrocket.union.ui.BlackToolbar
import com.itrocket.union.ui.ReservesItem

@Composable
fun ReservesScreen(
    state: ReservesStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onSearchClickListener: () -> Unit,
    onFilterClickListener: () -> Unit,
    onReservesListener: (ReservesDomain) -> Unit
) {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = appInsets.topInset.dp)
        ) {
            BlackToolbar(
                title = stringResource(id = R.string.reserves_title),
                onBackClickListener = onBackClickListener,
                onSearchClickListener = onSearchClickListener,
                onFilterClickListener = onFilterClickListener
            )
            when {
                state.isLoading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }
                state.reserves.isNotEmpty() -> {
                    ReservesList(
                        reserves = state.reserves,
                        navBarPadding = appInsets.bottomInset,
                        onReservesListener = {
                            onReservesListener(it)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ReservesList(
    reserves: List<ReservesDomain>,
    navBarPadding: Int,
    onReservesListener: (ReservesDomain) -> Unit
) {
    LazyColumn(Modifier.fillMaxSize()) {
        itemsIndexed(reserves, key = { index, item ->
            item.id
        }) { index, item ->
            val isShowBottomLine = reserves.lastIndex != index
            ReservesItem(
                reserves = item,
                onReservesListener = onReservesListener,
                isShowBottomLine = isShowBottomLine
            )
        }
        item {
            Spacer(modifier = Modifier.height(navBarPadding.dp))
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
fun ReservesScreenPreview() {
    ReservesScreen(ReservesStore.State(
        reserves = listOf(
            ReservesDomain(
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
            ),
            ReservesDomain(
                id = "2",
                title = "Бумага А4 «Русалочка-500 листов»",
                isBarcode = false,
                listInfo =
                listOf(
                    ObjectInfoDomain(
                        "Заводской номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный номер",
                        "таылватвлыавыалвыоалвыа"
                    )
                ),
                itemsCount = 56
            ),
            ReservesDomain(
                id = "3",
                title = "Бумага А4 «Русалочка-250 листов»",
                isBarcode = true,
                listInfo =
                listOf(
                    ObjectInfoDomain(
                        "Заводской номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный номер",
                        "таылватвлыавыалвыоалвыа"
                    )
                ),
                itemsCount = 167
            )
        )
    ), AppInsets(topInset = previewTopInsetDp), {}, {}, {}, {})
}