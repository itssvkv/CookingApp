package com.example.cookingapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore

object RealPathUtil {

    @SuppressLint("NewApi")
    fun getRealPathFromURI(context: Context, uri: Uri): String? {
        var filePath: String? = null
        val wholeID = DocumentsContract.getDocumentId(uri)

        // Split at colon, use second item in the array
        val id = wholeID.split(":")[1]

        val column = arrayOf(MediaStore.Images.Media.DATA)

        // where id is equal to
        val sel = "${MediaStore.Images.Media._ID}=?"

        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            column, sel, arrayOf(id), null
        )

        cursor?.use {
            val columnIndex = it.getColumnIndex(column[0])
            if (it.moveToFirst()) {
                filePath = it.getString(columnIndex)
            }
        }

        return filePath
    }
}
