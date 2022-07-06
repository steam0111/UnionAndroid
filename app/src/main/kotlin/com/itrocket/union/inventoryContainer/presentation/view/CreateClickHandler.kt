package com.itrocket.union.inventoryContainer.presentation.view

import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain

interface InventoryCreateClickHandler {

    fun onInventoryCreateClicked(inventoryCreate: InventoryCreateDomain)
}