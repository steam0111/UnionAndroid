package com.itrocket.union.serverConnect.presentation.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract

class CreateFileContract : ActivityResultContract<String, Uri?>() {
    override fun createIntent(context: Context, fileName: String): Intent =
        Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/union"
            putExtra(Intent.EXTRA_TITLE, fileName)
        }

    override fun parseResult(resultCode: Int, result: Intent?): Uri? {
        if (resultCode != Activity.RESULT_OK) {
            return null
        }
        return result?.data
    }
}