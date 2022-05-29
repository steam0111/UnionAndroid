package com.itrocket.union.nomenclatureGroup.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.core.base.AppInsets
import com.itrocket.union.R
import com.itrocket.union.nomenclatureGroup.domain.entity.NomenclatureGroupDomain
import com.itrocket.union.nomenclatureGroup.presentation.store.NomenclatureGroupStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BlackToolbar
import com.itrocket.union.ui.DefaultListItem

@Composable
fun NomenclatureGroupScreen(
    state: NomenclatureGroupStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit
) {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = appInsets.topInset.dp)
        ) {
            BlackToolbar(
                title = stringResource(id = R.string.nomenclature_group_title),
                onBackClickListener = onBackClickListener
            )
            Content(nomenclatureGroupsDomain = state.nomenclatureGroups, navBarPadding = appInsets.bottomInset)
        }
    }
}

@Composable
private fun Content(
    nomenclatureGroupsDomain: List<NomenclatureGroupDomain>,
    navBarPadding: Int
) {
    LazyColumn(Modifier.fillMaxSize()) {
        itemsIndexed(nomenclatureGroupsDomain, key = { index, item ->
            item.id
        }) { index, item ->
            val isShowBottomLine = nomenclatureGroupsDomain.lastIndex != index
            DefaultListItem(
                title = item.name,
                isShowBottomLine = isShowBottomLine
            )
        }
        item {
            Spacer(modifier = Modifier.height(navBarPadding.dp))
        }
    }
}

@Preview(name = "светлая тема экран - 6.3 (3040x1440)", showSystemUi = true, device = Devices.PIXEL_4_XL, uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "темная тема экран - 4,95 (1920 × 1080)", showSystemUi = true, device = Devices.NEXUS_5, uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "планшет", showSystemUi = true, device = Devices.PIXEL_C)
@Composable
fun NomenclatureGroupScreenPreview() {
    NomenclatureGroupScreen(NomenclatureGroupStore.State(
        nomenclatureGroups = listOf(
            NomenclatureGroupDomain(
                0,
                "name"
            ),
            NomenclatureGroupDomain(
                1,
                "name 1"
            ),
            NomenclatureGroupDomain(
                2,
                "name 2"
            ),
            NomenclatureGroupDomain(
                3,
                "name 3"
            )
        )
    ), AppInsets(), {})
}