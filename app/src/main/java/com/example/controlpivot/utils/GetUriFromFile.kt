package com.example.controlpivot.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

class GetUriFromFile {

    companion object{
        operator fun invoke(context: Context, file: File): Uri {
            return FileProvider.getUriForFile(context, "com.savent.erp.fileprovider", file)
        }
    }
}