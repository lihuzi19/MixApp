package com.lihuzi.lhzclassloader

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.util.Log

/**
 *@Author lining
 *@Date 2023/7/31
 *@DESC TODO
 **/
class LHZContentProvider : ContentProvider() {
    companion object {
        private const val TAG = "LHZContentProvider"
    }

    override fun onCreate(): Boolean {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "onCreate, return true")
        }
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? = null

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int = 0
}