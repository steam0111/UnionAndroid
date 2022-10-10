package com.itrocket.union.unionPermissions.domain

import com.itrocket.union.authMain.domain.dependencies.AuthMainRepository
import com.itrocket.union.authMain.domain.entity.MyConfigDomain
import com.itrocket.union.unionPermissions.domain.entity.Action
import com.itrocket.union.unionPermissions.domain.entity.Action.READ
import com.itrocket.union.unionPermissions.domain.entity.UnionPermission
import com.itrocket.union.unionPermissions.domain.entity.customPermissionToListUnionPermissions

class UnionPermissionsInteractor(private val authMainRepository: AuthMainRepository) {

    suspend fun canRead(unionPermission: UnionPermission): Boolean {
        val config = authMainRepository.getMyPreferencesConfig()

        if (checkConstantCondition(unionPermission, config)) {
            return true
        }
        val customPermissions = unionPermission.customPermissionToListUnionPermissions()
        val configPermissions = config.permissions ?: return false
        return if (customPermissions.isNotEmpty()) {
            val models = customPermissions.map { it.model }
            val permissions =
                configPermissions.filter { models.contains(it.model) }.map { it.action }
            Action.values().map { it.action }.find { permissions.contains(it) } != null
        } else {
            val permission =
                configPermissions.find { it.model == unionPermission.model } ?: return false
            Action.values().map { it.action }.contains(permission.action)
        }
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
        val permission = permissions.filter { it.model == unionPermission.model }.map { it.action }

        return permission.contains(Action.COMPLETE_WITHOUT_NFC.action)
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