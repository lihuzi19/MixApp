package com.example.myapplication.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PhoneUtils {
    companion object {
        @JvmStatic
        fun callPhone(context: Context, phone: String): Boolean {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) ==
                    PackageManager.PERMISSION_GRANTED) {
                var intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone))
                if (context.packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                    context.startActivity(intent)
                    return true
                }
            } else {
                ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.CALL_PHONE), 0)
            }
            return false
        }
    }
}