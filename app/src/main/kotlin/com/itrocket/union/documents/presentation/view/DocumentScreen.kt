package com.itrocket.union.documents.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.Scaffold
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
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.documents.domain.entity.DocumentDateType
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.documents.domain.entity.ObjectType
import com.itrocket.union.documents.presentation.store.DocumentStore
import com.itrocket.union.ui.BlackToolbar
import com.itrocket.union.ui.ButtonBottomBar
import com.itrocket.union.ui.DocumentDateItem
import com.itrocket.union.ui.DocumentInfoItem

@Composable
fun DocumentScreen(
    state: DocumentStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onSearchClickListener: () -> Unit,
    onFilterClickListener: () -> Unit,
    onDocumentClickListener: (String) -> Unit,
    onCreateRequestClickListener: () -> Unit,
    onDateArrowClickListener: (String) -> Unit
) {
    AppTheme {
        Scaffold(
            topBar = {
                BlackToolbar(
                    title = stringResource(
                        id = R.string.documents_title,
                        stringResource(id = state.type.titleId)
                    ),
                    onSearchClickListener = onSearchClickListener,
                    onBackClickListener = onBackClickListener,
                    onFilterClickListener = onFilterClickListener
                )
            },
            bottomBar = {
                ButtonBottomBar(
                    text = stringResource(id = R.string.documents_create_issue),
                    onClick = onCreateRequestClickListener
                )
            },
            content = {
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
                    state.documents.isNotEmpty() -> {
                        Content(
                            contentPadding = it,
                            documents = state.documents,
                            onDocumentClickListener = onDocumentClickListener,
                            rotatedDates = state.rotatedDates,
                            onDateArrowClickListener = onDateArrowClickListener
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(top = appInsets.topInset.dp, bottom = appInsets.bottomInset.dp)
        )
    }
}

@Composable
private fun Content(
    contentPadding: PaddingValues,
    documents: List<DocumentView>,
    onDocumentClickListener: (String) -> Unit,
    onDateArrowClickListener: (String) -> Unit,
    rotatedDates: List<String>,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        itemsIndexed(documents) { index, item ->
            when (item) {
                is DocumentView.DocumentDateView -> {
                    DocumentDateItem(
                        item = item,
                        onArrowClickListener = {
                            onDateArrowClickListener(item.date)
                        },
                        isRotated = rotatedDates.contains(item.date)
                    )
                }
                is DocumentView.DocumentItemView -> {
                    val isShowBottomLine =
                        index < documents.lastIndex && documents[index + 1] !is DocumentView.DocumentDateView
                    AnimatedVisibility(
                        visible = !rotatedDates.contains(item.date),
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        DocumentInfoItem(
                            item = item,
                            onDocumentClickListener = onDocumentClickListener,
                            isShowBottomLine = isShowBottomLine
                        )
                    }
                }
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
fun DocumentScreenPreview() {
    DocumentScreen(
        DocumentStore.State(
            type = DocumentTypeDomain.ALL, documents = listOf(
                DocumentView.DocumentDateView(date = "12.12.12", dateUi = "12.12.12", dayType = DocumentDateType.OTHER),
                DocumentView.DocumentItemView(
                    date = "12.12.12",
                    number = "123213",
                    time = "123123",
                    objectStatus = ObjectStatus.AVAILABLE,
                    documentInfo = listOf("DocumentInfo1", "DocumentInfo2", "DocumentInfo3"),
                    documentStatus = DocumentStatus.CREATED,
                    objectType = ObjectType.MAIN_ASSETS
                ),
                DocumentView.DocumentItemView(
                    date = "12.12.12",
                    number = "1232132",
                    time = "123123",
                    objectStatus = ObjectStatus.AVAILABLE,
                    documentInfo = listOf("DocumentInfo1", "DocumentInfo2", "DocumentInfo3"),
                    documentStatus = DocumentStatus.CREATED,
                    objectType = ObjectType.MAIN_ASSETS
                ), DocumentView.DocumentItemView(
                    date = "12.12.12",
                    number = "1232133",
                    time = "123123",
                    objectStatus = ObjectStatus.AVAILABLE,
                    documentInfo = listOf("DocumentInfo1", "DocumentInfo2", "DocumentInfo3"),
                    documentStatus = DocumentStatus.CREATED,
                    objectType = ObjectType.MAIN_ASSETS
                ),
                DocumentView.DocumentItemView(
                    date = "12.12.12",
                    number = "1232134",
                    time = "123123",
                    objectStatus = ObjectStatus.AVAILABLE,
                    documentInfo = listOf("DocumentInfo1", "DocumentInfo2", "DocumentInfo3"),
                    documentStatus = DocumentStatus.CREATED,
                    objectType = ObjectType.MAIN_ASSETS
                )
            )
        ),
        AppInsets(topInset = previewTopInsetDp),
        {},
        {},
        {},
        {},
        {},
        {}
    )
}