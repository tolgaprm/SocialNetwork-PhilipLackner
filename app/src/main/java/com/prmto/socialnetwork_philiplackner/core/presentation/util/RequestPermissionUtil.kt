package com.prmto.socialnetwork_philiplackner.core.presentation.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class RequestPermissionUtil {
    companion object {
        fun request(
            permission: String,
            context: Context,
            activity: Activity,
            onShowSnackbarRationale: () -> Unit = {},
            onPermissionLauncher: () -> Unit,
            onGalleryLauncher: () -> Unit
        ) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        permission
                    )
                ) {
                    onShowSnackbarRationale()
                }
                onPermissionLauncher()
            } else {
                onGalleryLauncher()
            }
        }

        fun getMediaAccessPermissionBySDK(): String {
            return if (Build.VERSION.SDK_INT > 32) {
                Manifest.permission.READ_MEDIA_IMAGES
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            }
        }
    }
}