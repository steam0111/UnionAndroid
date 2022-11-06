package com.itrocket.union.inventoryChoose.domain

import android.os.Parcelable
import androidx.annotation.StringRes
import com.itrocket.union.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class InventoryChooseActionType(@StringRes val titleId: Int) : Parcelable {
    SHOW_AO(titleId = R.string.inventory_create_open_ao),
    ADD_COMMENT(titleId = R.string.inventory_create_add_comment)
}