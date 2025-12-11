/*
 * SPDX-FileCopyrightText: 2021-2022 tytydraco
 * SPDX-FileCopyrightText: 2023-2024 s1m
 * SPDX-License-Identifier: BSD-2-Clause
 */
package com.draco.buoy.views

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.os.UserHandle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.draco.buoy.R
import com.draco.buoy.databinding.ActivityPermissionBinding
import com.draco.buoy.viewmodels.PermissionActivityViewModel
import com.google.android.material.snackbar.Snackbar

class PermissionActivity : AppCompatActivity() {
    private val viewModel: PermissionActivityViewModel by viewModels()

    private lateinit var binding: ActivityPermissionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.enableEdgeToEdge(window)
        binding = ActivityPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // TODO: maybe update like mainactivity
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
                        or WindowInsetsCompat.Type.displayCutout()
            )
            v.updatePadding(
                left = bars.left,
                top = bars.top,
                right = bars.right,
                bottom = bars.bottom,
            )
            WindowInsetsCompat.CONSUMED
        }


        val userId = UserHandle.getUserHandleForUid(this.taskId).toString().filter {
            it.isDigit()
        }

        binding.command.text = String.format(resources.getString(R.string.permission_command_with_user), userId)

        /* Copy ADB command to clipboard */
        binding.command.setOnClickListener {
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("ADB Command", binding.command.text.toString())
            clipboardManager.setPrimaryClip(clip)

            Snackbar.make(binding.command, R.string.copied, Snackbar.LENGTH_SHORT).show()
        }

        /* Once permission is granted, return */
        viewModel.permissionGranted.observe(this) {
            if (it == true)
                finish()
        }
    }

    /* Disallow exit */
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {}
}
