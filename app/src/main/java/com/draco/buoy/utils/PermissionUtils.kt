/*
 * SPDX-FileCopyrightText: 2021-2022 tytydraco
 * SPDX-FileCopyrightText: 2023-2024 s1m
 * SPDX-License-Identifier: BSD-2-Clause
 */
package com.draco.buoy.utils

import android.content.Context
import android.content.pm.PackageManager

class PermissionUtils {
    companion object {
        /**
         * Return true if the permission is granted, false otherwise
         */
        fun isPermissionsGranted(context: Context, permission: String): Boolean =
            context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }
}