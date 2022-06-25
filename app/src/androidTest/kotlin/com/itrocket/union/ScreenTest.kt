package com.itrocket.union

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import com.google.accompanist.pager.ExperimentalPagerApi
import com.itrocket.union.accountingObjectDetail.presentation.view.AccountingObjectDetailScreenPreview
import com.itrocket.union.accountingObjects.presentation.view.AccountingObjectScreenPreview
import com.itrocket.union.authUser.presentation.view.AuthUserScreenPreview
import com.itrocket.union.branchDetail.presentation.view.BranchDetailScreenPreview
import com.itrocket.union.counterparties.presentation.view.CounterpartyScreenPreview
import com.itrocket.union.branches.presentation.view.BranchesScreenPreview
import com.itrocket.union.conterpartyDetail.presentation.view.CounterpartyDetailScreenPreview
import com.itrocket.union.departmentDetail.presentation.view.DepartmentDetailScreenPreview
import com.itrocket.union.departments.presentation.view.DepartmentScreenPreview
import com.itrocket.union.documentCreate.presentation.view.DocumentCreateScreenPreview
import com.itrocket.union.documents.presentation.view.DocumentScreenPreview
import com.itrocket.union.documentsMenu.presentation.view.DocumentMenuScreenPreview
import com.itrocket.union.employeeDetail.presentation.view.EmployeeDetailScreen
import com.itrocket.union.filter.presentation.view.FilterScreen
import com.itrocket.union.employeeDetail.presentation.view.EmployeeDetailScreenPreview
import com.itrocket.union.employees.presentation.view.EmployeeScreenPreview
import com.itrocket.union.equipmentTypes.presentation.view.EquipmentTypesScreenPreview
import com.itrocket.union.inventories.presentation.view.InventoriesScreenPreview
import com.itrocket.union.inventory.presentation.view.InventoryScreenPreview
import com.itrocket.union.inventoryCreate.presentation.view.InventoryCreateScreenPreview
import com.itrocket.union.location.presentation.view.LocationScreenPreview
import com.itrocket.union.newAccountingObject.presentation.view.NewAccountingObjectScreenPreview
import com.itrocket.union.nomenclatureDetail.presentation.view.NomenclatureDetailScreenPreview
import com.itrocket.union.nomenclatureGroupDetail.presentation.view.NomenclatureGroupDetailScreenPreview
import com.itrocket.union.organizationDetail.presentation.view.OrganizationDetailScreenPreview
import com.itrocket.union.organizations.presentation.view.OrganizationsScreenPreview
import com.itrocket.union.producer.presentation.view.ProducerScreenPreview
import com.itrocket.union.readingMode.presentation.view.ReadingModeScreenPreview
import com.itrocket.union.regionDetail.presentation.view.RegionDetailScreenPreview
import com.itrocket.union.regions.presentation.view.RegionScreenPreview
import com.itrocket.union.reserveDetail.presentation.view.ReserveDetailScreenPreview
import com.itrocket.union.reserves.presentation.view.ReservesScreenPreview
import com.itrocket.union.serverConnect.presentation.view.ServerConnectScreenPreview
import com.itrocket.union.switcher.presentation.view.SwitcherScreenPreview
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
    fun `OrganizationsScreen`() {
        takeScreenShot {
            OrganizationsScreenPreview()
        }
    }

    @Test
    fun `DepartmentsScreen`() {
        takeScreenShot {
            DepartmentScreenPreview()
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
    fun `NewAccountingObject`() {
        takeScreenShot {
            NewAccountingObjectScreenPreview()
        }
    }

    @Test
    fun `Switcher`() {
        takeScreenShot {
            SwitcherScreenPreview()
        }
    }

    @Test
    fun `Counterparty`() {
        takeScreenShot {
            CounterpartyScreenPreview()
        }
    }

    @Test
    fun `Region`() {
        takeScreenShot {
            RegionScreenPreview()
        }
    }

    @Test
    fun `Branches`() {
        takeScreenShot {
            BranchesScreenPreview()
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
    fun `DepartmentDetail`() {
        takeScreenShot {
            DepartmentDetailScreenPreview()
        }
    }

    @Test
    fun `NomenclatureGroupDetail`() {
        takeScreenShot {
            NomenclatureGroupDetailScreenPreview()
        }
    }

    @Test
    fun `OrganizationDetail`() {
        takeScreenShot {
            OrganizationDetailScreenPreview()
        }
    }

    @Test
    fun `BranchDetail`() {
        takeScreenShot {
            BranchDetailScreenPreview()
        }
    }

    @Test
    fun `RegionDetail`() {
        takeScreenShot {
            RegionDetailScreenPreview()
        }
    }

    @Test
    fun `CounterpartyDetail`() {
        takeScreenShot {
            CounterpartyDetailScreenPreview()
        }
    }
}