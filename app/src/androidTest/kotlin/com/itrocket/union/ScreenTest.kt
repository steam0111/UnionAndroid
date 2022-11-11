package com.itrocket.union

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import com.google.accompanist.pager.ExperimentalPagerApi
import com.itrocket.union.accountingObjectDetail.presentation.view.AccountingObjectDetailScreenPreview
import com.itrocket.union.accountingObjects.presentation.view.AccountingObjectScreenPreview
import com.itrocket.union.authUser.presentation.view.AuthUserScreenPreview
import com.itrocket.union.changeScanData.presentation.view.ChangeScanDataScreenPreview
import com.itrocket.union.comment.presentation.view.CommentScreenPreview
import com.itrocket.union.conterpartyDetail.presentation.view.CounterpartyDetailScreenPreview
import com.itrocket.union.dataCollect.presentation.view.DataCollectScreenPreview
import com.itrocket.union.documentCreate.presentation.view.DocumentCreateScreenPreview
import com.itrocket.union.documents.presentation.view.DocumentScreenPreview
import com.itrocket.union.documentsMenu.presentation.view.DocumentMenuScreenPreview
import com.itrocket.union.employeeDetail.presentation.view.EmployeeDetailScreenPreview
import com.itrocket.union.employees.presentation.view.EmployeeScreenPreview
import com.itrocket.union.equipmentTypeDetail.presentation.view.EquipmentTypeDetailScreenPreview
import com.itrocket.union.equipmentTypes.presentation.view.EquipmentTypesScreenPreview
import com.itrocket.union.filter.presentation.view.FilterScreenPreview
import com.itrocket.union.identify.presentation.view.IdentifyScreenPreview
import com.itrocket.union.inventories.presentation.view.InventoriesScreenPreview
import com.itrocket.union.inventory.presentation.view.InventoryScreenPreview
import com.itrocket.union.inventoryChoose.presentation.view.InventoryChooseScreenPreview
import com.itrocket.union.inventoryCreate.presentation.view.InventoryCreateScreenPreview
import com.itrocket.union.location.presentation.view.LocationScreenPreview
import com.itrocket.union.nomenclatureDetail.presentation.view.NomenclatureDetailScreenPreview
import com.itrocket.union.nomenclatureGroupDetail.presentation.view.NomenclatureGroupDetailScreenPreview
import com.itrocket.union.producer.presentation.view.ProducerScreenPreview
import com.itrocket.union.producerDetail.presentation.view.ProducerDetailScreenPreview
import com.itrocket.union.readerPower.presentation.view.ReaderPowerScreenPreview
import com.itrocket.union.readingMode.presentation.view.ReadingModeScreenPreview
import com.itrocket.union.reserveDetail.presentation.view.ReserveDetailScreenPreview
import com.itrocket.union.reserves.presentation.view.ReservesScreenPreview
import com.itrocket.union.selectActionWithValuesBottomMenu.presentation.view.SelectActionWithValuesBottomMenuScreenPreview
import com.itrocket.union.serverConnect.presentation.view.ServerConnectScreenPreview
import com.itrocket.union.structural.view.StructuralScreenPreview
import com.itrocket.union.switcher.presentation.view.SwitcherScreenPreview
import com.itrocket.union.ui.ConfirmAlertDialogContentPreview
import com.itrocket.union.ui.ListDialogPreview
import com.karumi.shot.ScreenshotTest
import org.junit.Rule
import org.junit.Test

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalFoundationApi
class ScreensTest : ScreenshotTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun takeScreenShot(screen: @Composable () -> Unit) {
        composeTestRule.setContent {
            screen()
        }
        compareScreenshot(composeTestRule)
    }

    @Test
    fun `DocumentsMenuScreen`() {
        takeScreenShot {
            DocumentMenuScreenPreview()
        }
    }

    @Test
    fun `AccountingObjectsScreen`() {
        takeScreenShot {
            AccountingObjectScreenPreview()
        }
    }

    @Test
    fun `AccountingObjectDetailScreen`() {
        takeScreenShot {
            AccountingObjectDetailScreenPreview()
        }
    }

    @Test
    fun `ServerConnectScreen`() {
        takeScreenShot {
            ServerConnectScreenPreview()
        }
    }

    @Test
    fun `AuthUserScreen`() {
        takeScreenShot {
            AuthUserScreenPreview()
        }
    }

    @Test
    fun `DocumentsScreen`() {
        takeScreenShot {
            DocumentScreenPreview()
        }
    }

    @Test
    fun `FilterScreen`() {
        takeScreenShot {
            FilterScreenPreview()
        }
    }

    @Test
    fun `LocationScreen`() {
        takeScreenShot {
            LocationScreenPreview()
        }
    }

    @Test
    fun `ReadingModeScreen`() {
        takeScreenShot {
            ReadingModeScreenPreview()
        }
    }

    @Test
    fun `ReservesScreen`() {
        takeScreenShot {
            ReservesScreenPreview()
        }
    }

    @Test
    fun `ReservesDetailScreen`() {
        takeScreenShot {
            ReserveDetailScreenPreview()
        }
    }

    @Test
    fun `EmployeesScreen`() {
        takeScreenShot {
            EmployeeScreenPreview()
        }
    }

    @Test
    fun `InventoryCreate`() {
        takeScreenShot {
            InventoryCreateScreenPreview()
        }
    }

    @Test
    fun `InventoriesCreate`() {
        takeScreenShot {
            InventoriesScreenPreview()
        }
    }

    @Test
    fun `Inventory`() {
        takeScreenShot {
            InventoryScreenPreview()
        }
    }

    @Test
    fun `Switcher`() {
        takeScreenShot {
            SwitcherScreenPreview()
        }
    }

    @Test
    fun `Producers`() {
        takeScreenShot {
            ProducerScreenPreview()
        }
    }

    @Test
    fun `DocumentCreate`() {
        takeScreenShot {
            DocumentCreateScreenPreview()
        }
    }

    @Test
    fun `EquipmentTypes`() {
        takeScreenShot {
            EquipmentTypesScreenPreview()
        }
    }

    @Test
    fun `EmployeeDetail`() {
        takeScreenShot {
            EmployeeDetailScreenPreview()
        }
    }

    @Test
    fun `NomenclatureDetail`() {
        takeScreenShot {
            NomenclatureDetailScreenPreview()
        }
    }

    @Test
    fun `NomenclatureGroupDetail`() {
        takeScreenShot {
            NomenclatureGroupDetailScreenPreview()
        }
    }

    @Test
    fun `CounterpartyDetail`() {
        takeScreenShot {
            CounterpartyDetailScreenPreview()
        }
    }

    @Test
    fun `ProducerDetail`() {
        takeScreenShot {
            ProducerDetailScreenPreview()
        }
    }

    @Test
    fun `EquipmentTypeDetail`() {
        takeScreenShot {
            EquipmentTypeDetailScreenPreview()
        }
    }

    @Test
    fun `Identify`() {
        takeScreenShot {
            IdentifyScreenPreview()
        }
    }

    @Test
    fun `SelectActionWithValuesBottomMenu`() {
        takeScreenShot {
            SelectActionWithValuesBottomMenuScreenPreview()
        }
    }

    @Test
    fun `Structural`() {
        takeScreenShot {
            StructuralScreenPreview()
        }
    }

    @Test
    fun `ConfirmAlertDialog`() {
        takeScreenShot {
            ConfirmAlertDialogContentPreview()
        }
    }

    @Test
    fun `DataCollect`() {
        takeScreenShot {
            DataCollectScreenPreview()
        }
    }

    @Test
    fun `ChangeScanData`() {
        takeScreenShot {
            ChangeScanDataScreenPreview()
        }
    }

    @Test
    fun `ReaderPower`() {
        takeScreenShot {
            ReaderPowerScreenPreview()
        }
    }

    @Test
    fun `Comment`() {
        takeScreenShot {
            CommentScreenPreview()
        }
    }

    @Test
    fun `InventoryChoose`() {
        takeScreenShot {
            InventoryChooseScreenPreview()
        }
    }

    @Test
    fun `ListDialog`() {
        takeScreenShot {
            ListDialogPreview()
        }
    }
}