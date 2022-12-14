package com.itrocket.union.accountingObjectDetail.presentation.store

import android.net.Uri
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.core.navigation.ShowBottomSheetNavigationLabel
import com.itrocket.union.R
import com.itrocket.union.accountingObjectDetail.presentation.view.AccountingObjectDetailComposeFragment.Companion.ACCOUNTING_OBJECT_DETAIL_RESULT_CODE
import com.itrocket.union.accountingObjectDetail.presentation.view.AccountingObjectDetailComposeFragment.Companion.ACCOUNTING_OBJECT_DETAIL_RESULT_LABEL
import com.itrocket.union.accountingObjectDetail.presentation.view.AccountingObjectDetailComposeFragmentDirections
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.alertType.AlertType
import com.itrocket.union.changeScanData.domain.entity.ChangeScanType
import com.itrocket.union.changeScanData.presentation.store.ChangeScanDataArguments
import com.itrocket.union.changeScanData.presentation.view.ChangeScanDataComposeFragment
import com.itrocket.union.changeScanData.presentation.view.ChangeScanDataComposeFragment.Companion.CHANGE_SCAN_DATA_ARGS
import com.itrocket.union.image.domain.ImageDomain
import com.itrocket.union.imageViewer.presentation.store.ImageViewerArguments
import com.itrocket.union.labelType.domain.entity.LabelTypeDomain
import com.itrocket.union.labelType.presentation.store.LabelTypeArguments
import com.itrocket.union.readingMode.presentation.store.ReadingModeResult
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab

interface AccountingObjectDetailStore :
    Store<AccountingObjectDetailStore.Intent, AccountingObjectDetailStore.State, AccountingObjectDetailStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
        data class OnPageSelected(val selectedPage: Int) : Intent()
        object OnReadingModeClicked : Intent()
        object OnDocumentSearchClicked : Intent()
        object OnDocumentAddClicked : Intent()
        object OnMarkingClosed : Intent()
        object OnGenerateRfidClicked : Intent()
        object OnWriteEpcClicked : Intent()
        object OnDismissed : Intent()
        object OnWriteOffClicked : Intent()
        object OnTriggerPressed : Intent()
        object OnTriggerReleased : Intent()
        object OnRemoveBarcodeClicked : Intent()
        object OnRemoveRfidClicked : Intent()
        object OnAddImageClicked : Intent()
        object OnLabelTypeEditClicked : Intent()
        data class OnImageTaken(val success: Boolean) : Intent()
        data class OnImageClicked(val imageDomain: ImageDomain) : Intent()
        data class OnWriteEpcHandled(val rfid: String) : Intent()
        data class OnWriteEpcError(val error: String) : Intent()
        data class OnScanHandled(val scanData: String) : Intent()
        data class OnReadingModeTabChanged(val readingModeTab: ReadingModeTab) : Intent()
        data class OnManualInput(val readingModeResult: ReadingModeResult) : Intent()
        data class OnLabelTypeSelected(val labelTypeId: String) : Intent()
    }

    data class State(
        val accountingObjectDomain: AccountingObjectDomain,
        val isLoading: Boolean = false,
        val isImageLoading: Boolean = false,
        val readingMode: ReadingModeTab = ReadingModeTab.RFID,
        val selectedPage: Int = 0,
        val canUpdate: Boolean = false,
        val dialogType: AlertType = AlertType.NONE,
        val rfidError: String = "",
        val imageUri: Uri? = null,
        val images: List<ImageDomain> = listOf(),
    )

    sealed class Label {
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
        data class GoBack(override val result: AccountingObjectDetailResult? = null) : Label(),
            GoBackNavigationLabel {

            override val resultCode: String
                get() = ACCOUNTING_OBJECT_DETAIL_RESULT_CODE

            override val resultLabel: String
                get() = ACCOUNTING_OBJECT_DETAIL_RESULT_LABEL
        }

        data class ShowReadingMode(val readingMode: ReadingModeTab) : Label(),
            ShowBottomSheetNavigationLabel {

            override val arguments: Bundle
                get() = bundleOf()

            override val containerId: Int = R.id.mainActivityNavHostFragment

            override val fragment: Fragment
                get() = ReadingModeComposeFragment()
        }

        data class ShowChangeScanData(
            val entityId: String,
            val scanValue: String?,
            val newScanValue: String,
            val changeScanType: ChangeScanType
        ) : Label(),
            ShowBottomSheetNavigationLabel {
            override val arguments: Bundle
                get() = bundleOf(
                    CHANGE_SCAN_DATA_ARGS to ChangeScanDataArguments(
                        entityId = entityId,
                        scanValue = scanValue,
                        changeScanType = changeScanType,
                        newScanValue = newScanValue
                    )
                )
            override val containerId: Int
                get() = R.id.mainActivityNavHostFragment
            override val fragment: Fragment
                get() = ChangeScanDataComposeFragment()

        }

        data class ChangeSubscribeScanData(val isSubscribe: Boolean) : Label()

        data class ShowAddImage(val imageUri: Uri) : Label()

        data class ShowImageViewer(
            val images: List<ImageDomain>,
            val currentImage: ImageDomain,
            val accountingObjectId: String
        ) :
            Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = AccountingObjectDetailComposeFragmentDirections.actionAccountingObjectsDetailsToImageViewer(
                    ImageViewerArguments(
                        images = images,
                        currentImage = currentImage,
                        accountingObjectId = accountingObjectId
                    )
                )

        }

        object ShowLabelTypes : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = AccountingObjectDetailComposeFragmentDirections.toLabelType(
                    LabelTypeArguments(isSelectMode = true)
                )
        }
    }
}