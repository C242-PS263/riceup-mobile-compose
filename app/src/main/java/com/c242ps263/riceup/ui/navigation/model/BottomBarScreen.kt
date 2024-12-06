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

    data object Camera: BottomBar(
        route = "camera",
        titleResId = R.string.camera,
        icon = R.drawable.camera,
        iconFocused = R.drawable.camera
    )

    data object History: BottomBar(
        route = "history",
        titleResId = R.string.history,
        icon = R.drawable.history,
        iconFocused = R.drawable.history
    )

    data object Profile: BottomBar(
        route = "profile",
        titleResId = R.string.profile,
        icon = R.drawable.profile,
        iconFocused = R.drawable.profile
    )
}