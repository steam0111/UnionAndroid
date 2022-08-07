package com.itrocket.union.structural.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.structural.domain.dependencies.StructuralRepository
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.structural.domain.entity.StructuralDomain
import com.itrocket.utils.resolveItem

class StructuralInteractor(
    private val repository: StructuralRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getStructuralList(
        selectedStructural: StructuralDomain? = null,
        searchText: String = ""
    ) =
        withContext(coreDispatchers.io) {
            repository.getStructuralList(selectedStructural, searchText)
        }

    fun isNewStructuralist(newList: List<StructuralDomain>, oldList: List<StructuralDomain>): Boolean {
        return newList.isNotEmpty() && !newList.containsAll(oldList)
    }

    fun resolveNewStructural(
        selectedStructuralScheme: List<StructuralDomain>,
        selectedStructural: StructuralDomain,
        isRemoveLast: Boolean
    ): List<StructuralDomain> {
        val structurals = selectedStructuralScheme.toMutableList()
        if (isRemoveLast) {
            structurals.removeLastOrNull()
        }
        structurals.resolveItem(selectedStructural)
        return structurals
    }

    fun removeLastStructurals(
        selectedStructuralScheme: List<StructuralDomain>
    ): List<StructuralDomain> {
        val structurals = selectedStructuralScheme.toMutableList()
        if (structurals.isNotEmpty()) {
            structurals.removeLast()
        }
        return structurals
    }
}