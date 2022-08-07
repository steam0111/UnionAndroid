package com.itrocket.union.unionPermissions.domain

import com.itrocket.union.authMain.domain.dependencies.AuthMainRepository
import com.itrocket.union.unionPermissions.domain.entity.Action
import com.itrocket.union.unionPermissions.domain.entity.UnionPermission

class UnionPermissionsInteractor(private val authMainRepository: AuthMainRepository) {

    suspend fun canRead(unionPermission: UnionPermission): Boolean {
        if (unionPermission == UnionPermission.NO_NEED) {
            return true
        }

        val config = authMainRepository.getMyPreferencesConfig()
        if (config.isSuperUser) {
            return true
        }

        val permissions = config.permissions ?: return false
        val permission = permissions.find { it.model == unionPermission.model } ?: return false
        return Action.values().map { it.action }.contains(permission.action)
    }

    companion object {
        const val PERMISSION_TAG = "PERMISSION_TAG"
    }
}