package com.example.lampcolours.data.repositories.permissionRepo

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.example.lampcolours.REQUEST_ENABLE_BT

class PermissionRepoImpl(private val context: Context) {


    fun permissionsChecker(): Boolean {

        return ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.BLUETOOTH_CONNECT
        ) == PackageManager.PERMISSION_GRANTED
    }

}
