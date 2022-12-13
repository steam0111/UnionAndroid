package com.itrocket.union.serverConnect.presentation.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract

class OpenFileContract : ActivityResultContract<String, Uri?>() {
    override fun createIntent(context: Context, fileType: String): Intent =
        Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = fileType
        }

    override fun parseResult(resultCode: Int, result: Intent?): Uri? {
        if (resultCode != Activity.RESULT_OK) {
            return null
        }
        return result?.data
    }
}