package com.gama.alessandrogirardi.comunicacao_bluetooth_luva

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

import java.util.ArrayList

object PermissionUtils {

    /**
     * Solicita as permissÃµes
     */
     fun validate(activity: Activity, vararg permissions: String): Boolean {
        val list = ArrayList<String>()
        for (permission in permissions) {
            val ok = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
            if (!ok) {
                list.add(permission)
            }
        }
        if (list.isEmpty()) {
            return true
        }
        val newPermissions = list.toTypedArray()
        ActivityCompat.requestPermissions(activity, newPermissions, 2)
        return false
    }
}
