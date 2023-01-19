package com.itrocket.union.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatusType
import com.itrocket.union.accountingObjects.domain.entity.Status
import com.itrocket.union.common.DefaultItem
import com.itrocket.union.documents.domain.entity.DocumentDateType
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.documents.presentation.view.DocumentView
import com.itrocket.union.employees.domain.entity.EmployeeDomain
import com.itrocket.union.identify.domain.NomenclatureReserveDomain
import com.itrocket.union.inventories.domain.entity.InventoryStatus
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.StructuralParamDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.utils.clickableUnbounded
import java.math.BigDecimal

private const val MAX_LIST_INFO = 3
private const val DATE_ITEM_ROTATION_DURATION = 200

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountingObjectItem(
    accountingObject: AccountingObjectDomain,
    onAccountingObjectListener: (AccountingObjectDomain) -> Unit,
    onAccountingObjectLongClickListener: (AccountingObjectDomain) -> Unit = {},
    onDeleteClickListener: (String) -> Unit = {},
    onStatusClickListener: () -> Unit = {},
    status: Status?,
    isShowBottomLine: Boolean,
    statusText: String? = null,
    isEnabled: Boolean = true,
    isShowScanInfo: Boolean = true,
    canDelete: Boolean = false,
    showNonMarkingAttention: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(white)
            .combinedClickable(
                onClick = { onAccountingObjectListener(accountingObject) },
                onLongClick = { onAccountingObjectLongClickListener(accountingObject) },
                enabled = isEnabled
            ), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Bottom
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 16.dp, end = 16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                val headerText =
                    accountingObject.title.ifEmpty { stringResource(id = R.string.value_not_defined) }
                HeaderText(headerText)
                Spacer(modifier = Modifier.height(4.dp))
                accountingObject.listMainInfo.take(MAX_LIST_INFO).forEach {
                    Text(
                        text = stringResource(
                            R.string.common_two_dots,
                            it.title?.let { stringResource(id = it) }.orEmpty(),
                            it.value.orEmpty()
                        ), style = AppTheme.typography.subtitle1
                    )
                }
            }
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                status?.let {
                    SmallStatusLabel(
                        status = it,
                        text = statusText,
                        onClick = onStatusClickListener,
                        enabled = isEnabled

                    )
                }
                if (showNonMarkingAttention && !accountingObject.marked) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Image(
                        painter = painterResource(R.drawable.ic_attention_circle),
                        contentDescription = null
                    )
                }
                if (status is ObjectStatusType && isShowScanInfo && (accountingObject.hasBarcode || accountingObject.hasRfid)) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        if (accountingObject.hasBarcode) {
                            Text(
                                text = stringResource(R.string.common_barcode),
                                style = AppTheme.typography.caption,
                                color = AppTheme.colors.secondaryColor,
                                modifier = Modifier.padding(end = 4.dp)
                            )
                            RadioButton(
                                selected = accountingObject.isBarcode,
                                onClick = null,
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = AppTheme.colors.secondaryColor,
                                    unselectedColor = graphite3
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                    if (accountingObject.hasRfid) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = stringResource(R.string.common_rfid),
                                style = AppTheme.typography.caption,
                                color = AppTheme.colors.secondaryColor,
                                modifier = Modifier.padding(end = 4.dp)
                            )
                            RadioButton(
                                selected = accountingObject.isBarcode,
                                onClick = null,
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = AppTheme.colors.secondaryColor,
                                    unselectedColor = graphite3
                                )
                            )
                        }
                    }
                }
            }
        }
        if (canDelete) {
            Image(
                painter = painterResource(R.drawable.ic_delete),
                colorFilter = ColorFilter.tint(red5),
                contentDescription = null,
                modifier = Modifier
                    .clickableUnbounded(onClick = {
                        onDeleteClickListener(accountingObject.id)
                    })
                    .padding(vertical = 12.dp, horizontal = 16.dp)
            )
        } else {
            Spacer(modifier = Modifier.height(12.dp))
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
    onDeleteClickListener: (String) -> Unit = {},
    isShowBottomLine: Boolean,
    clickable: Boolean = true,
    canDelete: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(white)
            .clickable(onClick = {
                onReservesListener(reserves)
            }, enabled = clickable),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 12.dp, end = 16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                HeaderText(reserves.title)
                Spacer(modifier = Modifier.height(4.dp))
                reserves.listInfo.take(MAX_LIST_INFO).forEach {
                    Text(
                        text = stringResource(
                            R.string.common_two_dots,
                            it.title?.let { stringResource(id = it) }.orEmpty(),
                            it.value.orEmpty()
                        ), style = AppTheme.typography.subtitle1
                    )
                }
            }
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                Text(
                    text = reserves.itemsCount.toString(),
                    color = AppTheme.colors.mainTextColor,
                    style = AppTheme.typography.body2,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        if (canDelete) {
            Image(
                painter = painterResource(R.drawable.ic_delete),
                colorFilter = ColorFilter.tint(red5),
                contentDescription = null,
                modifier = Modifier
                    .clickableUnbounded(onClick = {
                        onDeleteClickListener(reserves.id)
                    })
                    .padding(vertical = 12.dp, horizontal = 16.dp)
            )
        } else {
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
    if (isShowBottomLine) {
        BottomLine()
    }
}

@Composable
fun InventoryDocumentItem(
    item: InventoryCreateDomain,
    onInventoryClickListener: () -> Unit = {},
    enabled: Boolean = false,
    isShowStatus: Boolean
) {
    val annotatedTitle = getInventoryAnnotatedTitle(item)
    val annotatedInfo = buildAnnotatedString {
        val filteredDocumentInfo = item.documentInfo.filter { it.value.isNotBlank() }
        filteredDocumentInfo.forEachIndexed { index, info ->
            append(info.value)

            if (index < filteredDocumentInfo.lastIndex) {
                append(" ")
                withStyle(
                    SpanStyle(
                        color = AppTheme.colors.mainColor, fontWeight = FontWeight.ExtraBold
                    )
                ) {
                    append("|")
                }
                append(" ")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(white)
            .clickable(enabled = enabled, onClick = onInventoryClickListener)
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                color = AppTheme.colors.mainTextColor,
                text = annotatedTitle.first,
                inlineContent = annotatedTitle.second,
                style = AppTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                lineHeight = 20.sp,
                modifier = Modifier
                    .padding(end = 40.dp)
                    .fillMaxWidth(0.7f)
            )
            if (isShowStatus) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
                    SmallStatusLabel(status = item.inventoryStatus, null)
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = annotatedInfo,
            style = AppTheme.typography.body2,
            color = AppTheme.colors.secondaryColor
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun NomenclatureReserveItem(
    nomenclatureReserveDomain: NomenclatureReserveDomain,
    onClick: (NomenclatureReserveDomain) -> Unit,
    isShowBottomLine: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(white)
            .clickable(onClick = { onClick(nomenclatureReserveDomain) })
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HeaderText(
                    text = nomenclatureReserveDomain.nomenclature,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = nomenclatureReserveDomain.count.toString(),
                    color = AppTheme.colors.mainTextColor,
                    style = AppTheme.typography.body2,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(
                    R.string.common_two_dots,
                    stringResource(R.string.label_type_title),
                    nomenclatureReserveDomain.labelType.orEmpty()
                ), style = AppTheme.typography.subtitle1
            )
            Text(
                text = stringResource(
                    R.string.common_two_dots,
                    stringResource(id = R.string.consignment_title),
                    nomenclatureReserveDomain.labelType.orEmpty()
                ), style = AppTheme.typography.subtitle1
            )

        }
        if (isShowBottomLine) {
            BottomLine()
        }
    }
}

private fun getInventoryAnnotatedTitle(item: InventoryCreateDomain): Pair<AnnotatedString, Map<String, InlineTextContent>> {
    val dateId = "date"
    val timeId = "time"
    val annotatedTitle = buildAnnotatedString {
        appendInlineContent(timeId, "[icon3]")
        append(item.getTextDate())
        append("  ")

        appendInlineContent(timeId, "[icon3]")
        append(item.getTextTime())
        append("  ")

    }
    val numberContent = mapOf(
        dateId to InlineTextContent(
            Placeholder(
                width = 20.sp,
                height = 16.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
            )
        ) {
            Image(painter = painterResource(R.drawable.ic_calendar), contentDescription = null)
        },
        timeId to InlineTextContent(
            Placeholder(
                width = 20.sp,
                height = 16.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
            )
        ) {
            Image(painter = painterResource(R.drawable.ic_clock), contentDescription = null)
        },
    )
    return annotatedTitle to numberContent
}

@Composable
fun DocumentInfoItem(
    item: DocumentView.DocumentItemView,
    onDocumentClickListener: (DocumentView.DocumentItemView) -> Unit,
    isShowBottomLine: Boolean,
    isShowStatus: Boolean = true
) {
    val timeId = "time"
    val statusId = "status"
    val annotatedTitle = buildAnnotatedString {
        appendInlineContent(timeId, "[icon2]")
        append(item.getTextTime())
        append("  ")

        appendInlineContent(statusId, "[icon3]")
        append(stringResource(item.documentType.titleId))
    }
    val numberContent = mapOf(
        timeId to InlineTextContent(
            Placeholder(
                width = 20.sp,
                height = 16.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
            )
        ) {
            Image(painter = painterResource(R.drawable.ic_clock), contentDescription = null)
        },
        statusId to InlineTextContent(
            Placeholder(
                width = 20.sp,
                height = 16.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            )
        ) {
            Image(painter = painterResource(R.drawable.ic_document), contentDescription = null)
        },
    )
    val annotatedInfo = buildAnnotatedString {
        val filteredParams = item.params.filter { it.value.isNotBlank() }
        filteredParams.forEachIndexed { index, info ->
            if (info.type == ManualType.STRUCTURAL_TO) {
                append(stringResource(R.string.documents_to))
                append(" ")
            } else if (info.type == ManualType.STRUCTURAL_FROM) {
                append(stringResource(R.string.documents_from))
                append(" ")
            }
            append(info.value)
            if (index < filteredParams.lastIndex) {
                append(" ")
                withStyle(
                    SpanStyle(
                        color = AppTheme.colors.mainColor, fontWeight = FontWeight.ExtraBold
                    )
                ) {
                    append("|")
                }
                append(" ")
            }
        }
    }
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(white)
        .clickable {
            onDocumentClickListener(item)
        }
        .padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = annotatedTitle,
                inlineContent = numberContent,
                style = AppTheme.typography.body1,
                color = AppTheme.colors.mainTextColor,
                fontWeight = FontWeight.Bold,
                lineHeight = 20.sp,
                modifier = Modifier
                    .padding(end = 40.dp)
                    .fillMaxWidth(0.7f)
            )
            if (isShowStatus) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
                    SmallStatusLabel(status = item.documentStatus, null)
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = annotatedInfo,
            style = AppTheme.typography.body2,
            color = AppTheme.colors.secondaryColor
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (isShowBottomLine) {
            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(psb4)
            )
        }
    }
}

@Composable
fun DocumentDateItem(
    item: DocumentView.DocumentDateView,
    onArrowClickListener: () -> Unit = {},
    isRotated: Boolean = false
) {
    val angle: Float by animateFloatAsState(
        targetValue = if (isRotated) 180F else 0F, animationSpec = tween(
            durationMillis = DATE_ITEM_ROTATION_DURATION, easing = FastOutSlowInEasing
        )
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(graphite2)
            .padding(vertical = 11.dp, horizontal = 16.dp)
    ) {
        Text(
            text = when (item.dayType) {
                DocumentDateType.TODAY -> stringResource(id = R.string.documents_today)
                DocumentDateType.YESTERDAY -> stringResource(id = R.string.documents_yesterday)
                DocumentDateType.OTHER -> item.dateUi
            },
            style = AppTheme.typography.subtitle1,
            color = AppTheme.colors.mainTextColor,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = if (item.dayType != DocumentDateType.OTHER) {
                "(${item.dateUi})"
            } else {
                ""
            }, style = AppTheme.typography.body2, color = graphite6
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.ic_arrow_up_small),
            contentDescription = null,
            modifier = Modifier
                .clickableUnbounded(onClick = {
                    onArrowClickListener()
                })
                .rotate(angle)
        )
    }
}

@Composable
fun DefaultListItem(
    item: DefaultItem, onItemClickListener: (DefaultItem) -> Unit, isShowBottomLine: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                onItemClickListener(item)
            })
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Column {
            HeaderText(item.title)
            Spacer(modifier = Modifier.height(4.dp))
            item.subtitles.forEach {
                if (!it.value.isNullOrBlank()) {
                    Text(
                        text = stringResource(
                            R.string.common_two_dots, stringResource(it.key), it.value.orEmpty()
                        ), style = AppTheme.typography.body2, color = AppTheme.colors.secondaryColor
                    )
                }
            }
        }
    }
    if (isShowBottomLine) {
        BottomLine()
    }
}

@Preview
@Composable
fun DocumentDateItemPreview() {
    DocumentDateItem(
        item = DocumentView.DocumentDateView(
            "12.12.12", DocumentDateType.OTHER, "12.12.12"
        ), {}, false
    )
}

@Preview
@Composable
fun DocumentInfoItemPreview() {
    DocumentInfoItem(
        item = DocumentView.DocumentItemView(
            id = "dd",
            number = "БП-00001374",
            documentStatus = DocumentStatus.CREATED,
            date = 123123,
            params = listOf(
                ParamDomain(
                    "1", "blbbb", type = ManualType.MOL
                ), ParamDomain(
                    "1", "blbbb", type = ManualType.LOCATION
                ), StructuralParamDomain(manualType = ManualType.STRUCTURAL)
            ),
            documentType = DocumentTypeDomain.WRITE_OFF,
            dateUi = "12.12.12",
            userInserted = "",
        ), onDocumentClickListener = {}, isShowBottomLine = true
    )
}


@Preview
@Composable
fun ReservesItemPreview() {
    ReservesItem(
        reserves = ReservesDomain(
            id = "1",
            title = "Авторучка «Зебра TR22»",
            isBarcode = true,
            listInfo = listOf(
                ObjectInfoDomain(
                    R.string.auth_main_title, "таылватвлыавыалвыоалвыа"
                ), ObjectInfoDomain(
                    R.string.auth_main_title, "таылватвлыавыалвыоалвыа"
                )
            ),
            itemsCount = BigDecimal(1200L),
            barcodeValue = "",
            labelTypeId = ":",
            nomenclatureId = "",
            consignment = "",
            unitPrice = "",
            bookKeepingInvoice = null,
        ), onReservesListener = {}, canDelete = true, isShowBottomLine = true
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
            status = ObjectStatus("available"),
            listMainInfo = listOf(
                ObjectInfoDomain(
                    R.string.auth_main_title, "таылватвлыавыалвыоалвыа"
                ),
                ObjectInfoDomain(
                    R.string.auth_main_title, "таылватвлыавыалвыоалвыа"
                ),
            ),
            listAdditionallyInfo = listOf(
                ObjectInfoDomain(
                    R.string.auth_main_title, "таылватвлыавыалвыоалвыа"
                ),
                ObjectInfoDomain(
                    R.string.auth_main_title, "таылватвлыавыалвыоалвыа"
                ),
            ),
            barcodeValue = "",
            rfidValue = "",
            factoryNumber = "",
            marked = false,
            characteristics = emptyList(),
        ),
        onAccountingObjectListener = {},
        onAccountingObjectLongClickListener = {},
        isShowBottomLine = true,
        status = ObjectStatusType(
            "",
            AppTheme.colors.secondaryColor.value.toString(),
            AppTheme.colors.secondaryColor.value.toString(),
            ""
        ),
        showNonMarkingAttention = true,
        canDelete = true
    )
}

@Preview
@Composable
fun DefaultListItemPreview() {
    DefaultListItem(
        item = DefaultItem(
            id = "1",
            title = "Organization",
            subtitles = mapOf(
                R.string.department_code to "value1", R.string.department_code to "value2"
            ),

            ), onItemClickListener = {}, isShowBottomLine = true
    )
}

@Preview
@Composable
fun InventoryDocumentItemPreview() {
    InventoryDocumentItem(
        item = InventoryCreateDomain(
            id = "",
            number = "БП-00001374",
            creationDate = System.currentTimeMillis(),
            documentInfo = listOf(
                ParamDomain("1", "Систмный интегратор", ManualType.MOL),
                ParamDomain("2", "Систмный интегратор", ManualType.MOL),
                ParamDomain("3", "Систмный интегратор", ManualType.MOL),
            ),
            accountingObjects = listOf(),
            inventoryStatus = InventoryStatus.CREATED,
            userInserted = "",
            userUpdated = "",
            nomenclatureRecords = listOf(),
            rfids = listOf()
        ), isShowStatus = true
    )
}

@Composable
fun EmployeeItem(
    item: EmployeeDomain,
    onEmployeeClickListener: (EmployeeDomain) -> Unit,
    isShowBottomLine: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onEmployeeClickListener(item) })
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            HeaderText(item.fullName)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(
                    R.string.employees_service_number, item.number
                ), style = AppTheme.typography.subtitle1, color = AppTheme.colors.secondaryColor
            )
        }
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = stringResource(R.string.employees_nfc),
                    style = AppTheme.typography.caption,
                    color = graphite5,
                    modifier = Modifier.padding(end = 4.dp)
                )
                RadioButton(
                    selected = item.hasNfc, onClick = null, colors = RadioButtonDefaults.colors(
                        selectedColor = AppTheme.colors.mainColor, unselectedColor = graphite3
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
fun EmployeeItemPreview() {
    EmployeeItem(
        item = EmployeeDomain(
            id = "1",
            nfc = null,
            catalogItemName = "Ким У Бин",
            number = "BO_173470001290",
            firstname = "Ким",
            lastname = "У",
            patronymic = "Бин",
            post = "bb",
        ), onEmployeeClickListener = {}, isShowBottomLine = true
    )
}

@Composable
private fun HeaderText(text: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = text,
        fontWeight = FontWeight.Bold,
        style = AppTheme.typography.h6,
        fontSize = 19.sp,
        color = AppTheme.colors.mainTextColor
    )
}

@Composable
fun ScanningObjectItem(
    scanningObject: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = scanningObject
            )
        }
    }
}

@Composable
@Preview
private fun NomenclatureReserveItemPreview() {
    NomenclatureReserveItem(
        nomenclatureReserveDomain = NomenclatureReserveDomain(
            "aas",
            "vcvcv",
            "fsdf",
            "fsdf",
            "fsdf",
            12
        ), onClick = {}, isShowBottomLine = false
    )
}