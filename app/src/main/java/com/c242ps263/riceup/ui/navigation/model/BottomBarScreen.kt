package com.c242ps263.riceup.ui.navigation.model

import com.c242ps263.riceup.R

sealed class BottomBarScreen() {
    data object Home : BottomBar(
        route = "home",
        titleResId = R.string.home,
        icon = R.drawable.home,
        iconFocused = R.drawable.home
    )

    data object Prediction: BottomBar(
        route = "prediction",
        titleResId = R.string.prediction,
        icon = R.drawable.calendar,
        iconFocused = R.drawable.calendar
    )

    data object History: BottomBar(
        title = "RIWAYAT",
        route = "history",
        titleResId = R.string.history,
        icon = R.drawable.history,
        iconFocused = R.drawable.history
    )

    data object Detection: BottomBar(
        title = "SCAN",
        route = "detection",
        titleResId = R.string.detection,
        icon = R.drawable.camera,
        iconFocused = R.drawable.camera
    )

    data object Profile: BottomBar(
        title = "PROFILE",
        route = "profile",
        titleResId = R.string.profile,
        icon = R.drawable.profile,
        iconFocused = R.drawable.profile
    )
}