package com.itrocket.union

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import com.google.accompanist.pager.ExperimentalPagerApi
import com.itrocket.union.accountingObjectDetail.presentation.view.AccountingObjectDetailScreenPreview
import com.itrocket.union.accountingObjects.presentation.view.AccountingObjectScreenPreview
import com.itrocket.union.authUser.presentation.view.AuthUserScreenPreview
import com.itrocket.union.documents.presentation.view.DocumentScreenPreview
import com.itrocket.union.documentsMenu.presentation.view.DocumentMenuScreenPreview
import com.itrocket.union.filter.domain.entity.FilterDomain
import com.itrocket.union.filter.domain.entity.FilterValueType
import com.itrocket.union.filter.presentation.view.FilterScreenPreview
import com.itrocket.union.filterValues.presentation.view.FilterValueScreenPreview
import com.itrocket.union.location.presentation.view.LocationScreenPreview
import com.itrocket.union.readingMode.presentation.view.ReadingModeScreenPreview
import com.itrocket.union.reserveDetail.presentation.view.ReserveDetailScreenPreview
import com.itrocket.union.reserves.presentation.view.ReservesScreenPreview
import com.itrocket.union.serverConnect.presentation.view.ServerConnectScreenPreview
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
    fun `FilterValueScreen_typeMultiSelect`() {
        takeScreenShot {
            FilterValueScreenPreview()
        }
    }

    @Test
    fun `FilterValueScreen_typeSingleSelect`() {
        takeScreenShot {
            FilterValueScreenPreview(
                filter = FilterDomain(
                    name = "Filter name",
                    filterValueType = FilterValueType.SINGLE_SELECT_LIST,
                    valueList = listOf("Value1", "Value2", "Value3")
                )
            )
        }
    }

    @Test
    fun `FilterValueScreen_typeInput`() {
        takeScreenShot {
            FilterValueScreenPreview(
                filter = FilterDomain(
                    name = "Filter name",
                    filterValueType = FilterValueType.INPUT
                )
            )
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
}