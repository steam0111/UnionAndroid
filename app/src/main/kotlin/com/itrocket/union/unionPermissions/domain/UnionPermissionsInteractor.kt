package com.itrocket.union.unionPermissions.domain

import com.itrocket.union.authMain.domain.dependencies.AuthMainRepository
import com.itrocket.union.authMain.domain.entity.MyConfigDomain
import com.itrocket.union.unionPermissions.domain.entity.Action
import com.itrocket.union.unionPermissions.domain.entity.Action.READ
import com.itrocket.union.unionPermissions.domain.entity.UnionPermission

class UnionPermissionsInteractor(private val authMainRepository: AuthMainRepository) {

    suspend fun canRead(unionPermission: UnionPermission): Boolean {
        val config = authMainRepository.getMyPreferencesConfig()

        if (checkConstantCondition(unionPermission, config)) {
            return true
        }

        val permissions = config.permissions ?: return false
        val permission = permissions.find { it.model == unionPermission.model } ?: return false
        return Action.values().map { it.action }.contains(permission.action)
    }

    suspend fun canCreate(unionPermission: UnionPermission): Boolean {
        val config = authMainRepository.getMyPreferencesConfig()

        if (checkConstantCondition(unionPermission, config)) {
            return true
        }

        val permissions = config.permissions ?: return false
        val permission = permissions.find { it.model == unionPermission.model } ?: return false

        val actions = Action.values().toMutableList()
        actions.remove(READ)

        return actions.map { it.action }.contains(permission.action)
    }

    suspend fun canConductDocument(unionPermission: UnionPermission): Boolean {
        val config = authMainRepository.getMyPreferencesConfig()

        if (checkConstantCondition(unionPermission, config)) {
            return true
        }

        val permissions = config.permissions ?: return false
        val permission = permissions.find { it.model == unionPermission.model } ?: return false

        return permission.action == Action.COMPLETE_WITHOUT_NFC.action
    }

    private fun checkConstantCondition(
        unionPermission: UnionPermission,
        config: MyConfigDomain
    ): Boolean {
        if (unionPermission == UnionPermission.NO_NEED) {
            return true
        }
        if (config.isSuperUser) {
            return true
        }
        return false
    }

    companion object {
        const val PERMISSION_TAG = "PERMISSION_TAG"
    }
}