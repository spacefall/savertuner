/*
 * SPDX-FileCopyrightText: 2021-2022 tytydraco
 * SPDX-FileCopyrightText: 2023-2024 s1m
 * SPDX-License-Identifier: BSD-2-Clause
 */
package com.draco.buoy.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.FragmentContainerView
import com.draco.buoy.R
import com.draco.buoy.databinding.ActivityMainBinding
import com.draco.buoy.fragments.MainPreferenceFragment
import com.draco.buoy.utils.PermissionUtils

class MainActivity : AppCompatActivity() {
    private lateinit var preferences: FragmentContainerView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.enableEdgeToEdge(window)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferences = binding.preferences

        // TODO: make nicer bottom
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
                        or WindowInsetsCompat.Type.displayCutout()
            )
            v.updatePadding(
                left = bars.left,
                top = bars.top,
                right = bars.right,
                //bottom = bars.bottom,
            )
            WindowInsetsCompat.CONSUMED
        }

        /* If we are missing a permission, lock the user in the permission activity */
        if (!PermissionUtils.isPermissionsGranted(this, android.Manifest.permission.WRITE_SECURE_SETTINGS))
            goToPermissionActivity()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.preferences, MainPreferenceFragment())
            .commit()
    }

    private fun goToPermissionActivity() {
        val intent = Intent(this, PermissionActivity::class.java)
        startActivity(intent)
    }
}