/*
 * SPDX-FileCopyrightText: 2021-2022 tytydraco
 * SPDX-FileCopyrightText: 2023-2024 s1m
 * SPDX-License-Identifier: BSD-2-Clause
 */
package com.draco.buoy.services

import com.draco.buoy.repositories.profiles.Profile

class TileHighService : ProfileTileService() {
    override val profile = Profile.HIGH
}