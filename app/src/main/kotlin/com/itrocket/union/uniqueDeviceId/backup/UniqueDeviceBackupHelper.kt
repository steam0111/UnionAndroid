package com.itrocket.union.uniqueDeviceId.backup

import android.app.backup.BackupAgentHelper
import android.app.backup.SharedPreferencesBackupHelper
import com.itrocket.union.uniqueDeviceId.UniqueDeviceIdModule.UNIQUE_DEVICE_ID_FILE_NAME

class UniqueDeviceBackupHelper : BackupAgentHelper() {

    override fun onCreate() {
        SharedPreferencesBackupHelper(this, UNIQUE_DEVICE_ID_FILE_NAME).also {
            addHelper(UNIQUE_DEVICE_BACKUP_KEY, it)
        }
    }

    companion object {
        private const val UNIQUE_DEVICE_BACKUP_KEY = "UNIQUE_DEVICE_BACKUP_KEY"
    }
}