package com.itrocket.union.branchDetail.presentation.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.branchDetail.domain.entity.BranchDetailDomain
import com.itrocket.union.branchDetail.presentation.store.BranchDetailStore
import com.itrocket.union.organizationDetail.domain.entity.OrganizationDetailDomain
import com.itrocket.union.organizationDetail.presentation.store.OrganizationDetailStore
import com.itrocket.union.ui.*

@Composable
fun BranchDetailScreen(
    state: BranchDetailStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit
) {
    AppTheme {
        Scaffold(
            topBar = {
                Toolbar(
                    onBackClickListener = onBackClickListener
                )
            },
            modifier = Modifier.padding(
                top = appInsets.topInset.dp,
                bottom = appInsets.bottomInset.dp
            )
        ) {
            Column(modifier = Modifier.padding(it)) {
                Spacer(modifier = Modifier.height(16.dp))
                ListInfoItem(listInfo = state.item.listInfo)
            }
        }
    }
}

@Composable
private fun Toolbar(
    onBackClickListener: () -> Unit
) {
    BaseToolbar(
        title = stringResource(id = R.string.branch),
        startImageId = R.drawable.ic_cross,
        onStartImageClickListener = onBackClickListener,
        backgroundColor = psb1,
        textColor = white
    )
}

@Composable
private fun ListInfoItem(listInfo: List<ObjectInfoDomain>) {
    LazyColumn {
        items(listInfo) { item ->
            ExpandedInfoField(
                label = stringResource(id = item.title),
                value = item.value ?: item.valueRes?.let { stringResource(id = it) }.orEmpty(),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(
    name = "светлая тема экран - 6.3 (3040x1440)",
    showSystemUi = true,
    device = Devices.PIXEL_4_XL,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "темная тема экран - 4,95 (1920 × 1080)",
    showSystemUi = true,
    device = Devices.NEXUS_5,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(name = "планшет", showSystemUi = true, device = Devices.PIXEL_C)
@Composable
fun BranchDetailScreenPreview() {
    BranchDetailScreen(
        BranchDetailStore.State(
            item = BranchDetailDomain(
                listInfo =
                listOf(
                    ObjectInfoDomain(
                        R.string.organization,
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        R.string.branches_title,
                        "таылватвлыавыалвыоалвыа"
                    )
                )
            )
        ), AppInsets(topInset = previewTopInsetDp), {})
}