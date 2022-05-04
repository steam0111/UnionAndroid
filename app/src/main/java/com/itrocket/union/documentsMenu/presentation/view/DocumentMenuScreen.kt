package com.itrocket.union.documentsMenu.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.R
import com.itrocket.union.documentsMenu.domain.entity.DocumentMenuDomain
import com.itrocket.union.documentsMenu.presentation.store.DocumentMenuStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BaseToolbar
import com.itrocket.union.ui.psb1
import com.itrocket.union.ui.white
import com.itrocket.utils.clickableUnbounded

@ExperimentalFoundationApi
@Composable
fun DocumentMenuScreen(
    state: DocumentMenuStore.State,
    appInsets: AppInsets,
    onDocumentItemClick: (DocumentMenuDomain) -> Unit,
    onProfileIconClick: () -> Unit
) {
    AppTheme {
        Scaffold(topBar = {
            BaseToolbar(
                title = state.userName,
                startImageId = R.drawable.ic_user,
                onStartImageClickListener = onProfileIconClick
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_question),
                    contentDescription = null
                )
            }
        }, content = {
            if (state.documents.isNotEmpty()) {
                DocumentList(
                    documents = state.documents,
                    onDocumentItemClick = onDocumentItemClick,
                    contentPadding = it
                )
            }
        }, modifier = Modifier
            .background(white)
            .padding(top = appInsets.topInset.dp, bottom = appInsets.bottomInset.dp)
        )
    }
}

@ExperimentalFoundationApi
@Composable
private fun DocumentList(
    documents: List<DocumentMenuDomain>,
    onDocumentItemClick: (DocumentMenuDomain) -> Unit,
    contentPadding: PaddingValues
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        modifier = Modifier
            .padding(contentPadding)
            .background(white)
            .fillMaxWidth()
    ) {
        items(documents) { item ->
            DocumentItem(documentMenuItem = item, onClick = onDocumentItemClick)
        }
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun DocumentItem(
    documentMenuItem: DocumentMenuDomain,
    onClick: (DocumentMenuDomain) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 32.dp)
            .clickableUnbounded {
                onClick(documentMenuItem)
            }
    ) {
        Image(
            painter = painterResource(documentMenuItem.iconId),
            contentDescription = null,
            modifier = Modifier
                .background(
                    psb1,
                    CircleShape
                )
                .padding(documentMenuItem.paddings.dp)
        )
        Text(
            text = stringResource(documentMenuItem.titleId),
            style = AppTheme.typography.subtitle2,
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}

@ExperimentalFoundationApi
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
fun DocumentMenuScreenPreview() {
    DocumentMenuScreen(
        DocumentMenuStore.State(
            documents = listOf(
                DocumentMenuDomain(
                    titleId = R.string.main_identification,
                    iconId = R.drawable.ic_indentyfication
                ),
                DocumentMenuDomain(titleId = R.string.main_marking, iconId = R.drawable.ic_marking),
                DocumentMenuDomain(
                    titleId = R.string.main_accounting_object,
                    iconId = R.drawable.ic_accounting_object
                ),
                DocumentMenuDomain(
                    titleId = R.string.main_reserves,
                    iconId = R.drawable.ic_reserves
                ),
                DocumentMenuDomain(
                    titleId = R.string.main_employees,
                    iconId = R.drawable.ic_employees
                ),
                DocumentMenuDomain(
                    titleId = R.string.main_documents,
                    iconId = R.drawable.ic_documentation,
                    paddings = 9
                ),
                DocumentMenuDomain(
                    titleId = R.string.main_commissioning,
                    iconId = R.drawable.ic_commisioning
                ),
                DocumentMenuDomain(titleId = R.string.main_issue, iconId = R.drawable.ic_issue),
                DocumentMenuDomain(titleId = R.string.main_return, iconId = R.drawable.ic_return),
                DocumentMenuDomain(titleId = R.string.main_moved, iconId = R.drawable.ic_moved),
                DocumentMenuDomain(
                    titleId = R.string.main_write_off,
                    iconId = R.drawable.ic_write_off
                ),
                DocumentMenuDomain(
                    titleId = R.string.main_inventory,
                    iconId = R.drawable.ic_inventory
                ),
            )
        ), AppInsets(topInset = previewTopInsetDp), {}) {}
}