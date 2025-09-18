package com.dharamveer.spendwise.export

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File

object FileProviderUtil {
    fun getShareIntent(context: Context, file: File, type: String): Intent {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
        return Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_STREAM, uri)
            this.type = type
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }
}
