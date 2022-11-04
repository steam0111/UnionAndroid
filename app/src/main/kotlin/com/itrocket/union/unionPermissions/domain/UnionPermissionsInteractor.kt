package com.itrocket.union.unionPermissions.domain

import android.util.Log
import com.itrocket.union.authMain.domain.dependencies.AuthMainRepository
import com.itrocket.union.authMain.domain.entity.MyConfigDomain
import com.itrocket.union.authMain.domain.entity.MyConfigPermission
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
            isAnyPermissionActionKeep(
                models = models,
                configPermissions = configPermissions,
                action = READ.action
            )
        } else {
            isPermissionActionKeep(
                configPermissions = configPermissions,
                checkPermission = unionPermission,
                checkAction = READ.action
            )
        }
    }

    suspend fun canCreate(unionPermission: UnionPermission): Boolean {
        return canMakeAction(unionPermission = unionPermission, action = Action.CREATE.action)
    }

    suspend fun canUpdate(unionPermission: UnionPermission): Boolean {
        return canMakeAction(unionPermission = unionPermission, action = Action.UPDATE.action)
    }

    suspend fun canConductDocument(unionPermission: UnionPermission): Boolean {
        return canMakeAction(
            unionPermission = unionPermission,
            action = Action.COMPLETE_WITHOUT_NFC.action
        )
    }

    suspend fun canCompleteInventory(unionPermission: UnionPermission): Boolean {
        return canMakeAction(
            unionPermission = unionPermission,
            action = Action.COMPLETE_INVENTORY.action
        )
    }

    suspend fun canMakeAction(unionPermission: UnionPermission, action: String): Boolean {
        val config = authMainRepository.getMyPreferencesConfig()

        if (checkConstantCondition(unionPermission, config)) {
            return true
        }

        val configPermissions = config.permissions ?: return false
        return isPermissionActionKeep(
            configPermissions = configPermissions,
            checkPermission = unionPermission,
            checkAction = action
        )
    }

    /**
     * @param configPermissions - Все разрешения пользователя
     * @param checkPermission - Проверяемое на наличие разрешение
     * @param checkAction - Проверяемое на наличие действие по пермишену
     * Метод для проверки наличия конкретного разрешения
     */
    private fun isPermissionActionKeep(
        configPermissions: List<MyConfigPermission>,
        checkPermission: UnionPermission,
        checkAction: String
    ): Boolean {
        val permissions =
            configPermissions.filter { it.model == checkPermission.model }.map { it.action }
        return permissions.contains(checkAction)
    }

    /**
     * @param models - Список моделей на проверку наличия их разрешений
     * @param checkPermission - Проверяемое на наличие разрешение
     * @param checkAction - Проверяемое на наличие действие по пермишену
     * Метод для проверки наличия разрешения хотя бы у одной из списка моделей
     */
    private fun isAnyPermissionActionKeep(
        models: List<String>,
        configPermissions: List<MyConfigPermission>,
        action: String
    ): Boolean {
        val permissions =
            configPermissions.filter { models.contains(it.model) }.map { it.action }
        return permissions.contains(action)
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