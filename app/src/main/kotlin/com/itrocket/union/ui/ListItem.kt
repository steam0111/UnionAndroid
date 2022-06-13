package com.itrocket.union.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.itrocket.union.accountingObjects.domain.entity.Status
import com.itrocket.union.documents.domain.entity.DocumentDateType
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.documents.domain.entity.ObjectType
import com.itrocket.union.documents.presentation.view.DocumentView
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.utils.clickableUnbounded

private const val MAX_LIST_INFO = 3
private const val DATE_ITEM_ROTATION_DURATION = 200

@Composable
fun DefaultListItem(
    title: String,
    isShowBottomLine: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Column {
            Text(
                text = title,
                style = AppTheme.typography.body1,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
    if (isShowBottomLine) {
        BottomLine()
    }
}

@Composable
fun AccountingObjectItem(
    accountingObject: AccountingObjectDomain,
    onAccountingObjectListener: (AccountingObjectDomain) -> Unit,
    status: Status,
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
            SmallStatusLabel(status = status)
            if (status is ObjectStatus) {
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

@Composable
fun InventoryDocumentItem(
    item: InventoryCreateDomain
) {
    val annotatedTitle = getInventoryAnnotatedTitle(item)
    val annotatedInfo = buildAnnotatedString {
        item.documentInfo.forEachIndexed { index, info ->
            append(info)
            if (index < item.documentInfo.lastIndex) {
                append(" ")
                withStyle(SpanStyle(color = psb6, fontWeight = FontWeight.ExtraBold)) {
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
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = annotatedTitle.first,
                inlineContent = annotatedTitle.second,
                style = AppTheme.typography.body1,
                fontWeight = FontWeight.Medium,
                lineHeight = 20.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = annotatedInfo, style = AppTheme.typography.caption, color = graphite6)
        Spacer(modifier = Modifier.height(12.dp))
    }
}

private fun getInventoryAnnotatedTitle(item: InventoryCreateDomain): Pair<AnnotatedString, Map<String, InlineTextContent>> {
    val numberId = "number"
    val dateId = "date"
    val timeId = "time"
    val annotatedTitle = buildAnnotatedString {
        appendInlineContent(numberId, "[icon1]")
        append(item.number)
        append("  ")

        appendInlineContent(timeId, "[icon3]")
        append(item.getTextDate())
        append("  ")

        appendInlineContent(timeId, "[icon3]")
        append(item.time)
        append("  ")

    }
    val numberContent = mapOf(
        numberId to InlineTextContent(
            Placeholder(
                width = 20.sp,
                height = 16.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
            )
        ) {
            Image(painter = painterResource(R.drawable.ic_number), contentDescription = null)
        },
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
    onDocumentClickListener: (String) -> Unit,
    isShowBottomLine: Boolean,
    isShowStatus: Boolean = true
) {
    val numberId = "number"
    val timeId = "time"
    val statusId = "status"
    val objectTypeId = "objectType"
    val annotatedTitle = buildAnnotatedString {
        appendInlineContent(numberId, "[icon1]")
        append(item.number)
        append("  ")

        appendInlineContent(timeId, "[icon2]")
        append(item.time)
        append("  ")

        appendInlineContent(statusId, "[icon3]")
        append(stringResource(item.documentStatus.textId))
        append("  ")

        appendInlineContent(objectTypeId, "[icon4]")
        append(stringResource(item.objectType.textId))
    }
    val numberContent = mapOf(
        numberId to InlineTextContent(
            Placeholder(
                width = 20.sp,
                height = 16.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
            )
        ) {
            Image(painter = painterResource(R.drawable.ic_number), contentDescription = null)
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
        statusId to InlineTextContent(
            Placeholder(
                width = 20.sp,
                height = 16.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            )
        ) {
            Image(painter = painterResource(R.drawable.ic_document), contentDescription = null)
        },
        objectTypeId to InlineTextContent(
            Placeholder(
                width = 20.sp,
                height = 16.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            )
        ) {
            Image(painter = painterResource(R.drawable.ic_category), contentDescription = null)
        },
    )
    val annotatedInfo = buildAnnotatedString {
        item.documentInfo.forEachIndexed { index, info ->
            append(info)
            if (index < item.documentInfo.lastIndex) {
                append(" ")
                withStyle(SpanStyle(color = psb6, fontWeight = FontWeight.ExtraBold)) {
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
            .clickable {
                onDocumentClickListener(item.number)
            }
            .padding(horizontal = 16.dp)
    ) {
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
                fontWeight = FontWeight.Medium,
                lineHeight = 20.sp,
                modifier = Modifier
                    .padding(end = 40.dp)
                    .fillMaxWidth(0.7f)
            )
            if (isShowStatus) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
                    SmallStatusLabel(status = item.documentStatus)
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = annotatedInfo, style = AppTheme.typography.caption, color = graphite6)
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
        targetValue = if (isRotated) 180F else 0F,
        animationSpec = tween(
            durationMillis = DATE_ITEM_ROTATION_DURATION,
            easing = FastOutSlowInEasing
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
            style = AppTheme.typography.subtitle2,
            color = psb1
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = if (item.dayType != DocumentDateType.OTHER) {
                "(${item.dateUi})"
            } else {
                ""
            },
            style = AppTheme.typography.body2,
            color = graphite6
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

@Preview
@Composable
fun DocumentDateItemPreview() {
    DocumentDateItem(
        item = DocumentView.DocumentDateView(
            "12.12.12",
            DocumentDateType.OTHER,
            "12.12.12"
        ), {}, false
    )
}

@Preview
@Composable
fun DocumentInfoItemPreview() {
    DocumentInfoItem(
        item = DocumentView.DocumentItemView(
            number = "БП-00001374",
            time = "12:40",
            objectStatus = ObjectStatus.AVAILABLE,
            documentInfo = listOf(
                "Систмный интегратор",
                "Систмный интегратор",
                "Систмный интегратор",
                "Систмный интегратор",
                "Систмный интегратор",
            ),
            documentStatus = DocumentStatus.CREATED,
            objectType = ObjectType.MAIN_ASSETS,
            date = ""
        ), onDocumentClickListener = {}, isShowBottomLine = true
    )
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
        ), onAccountingObjectListener = {}, isShowBottomLine = true,
        status = ObjectStatus.UNDER_REVIEW
    )
}

@Preview
@Composable
fun DefaultItemPreview() {
    DefaultListItem(
        title = "title title title title title title title title title",
        isShowBottomLine = true
    )
}

@Preview
@Composable
fun InventoryDocumentItemPreview() {
    InventoryDocumentItem(
        item = InventoryCreateDomain(
            number = "БП-00001374",
            time = "12:40",
            date = "12.12.12",
            documentInfo = listOf(
                "Систмный интегратор",
                "Систмный интегратор",
                "Систмный интегратор",
                "Систмный интегратор",
                "Систмный интегратор",
            )
        )
    )
}