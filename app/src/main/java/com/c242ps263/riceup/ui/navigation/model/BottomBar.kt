package com.c242ps263.riceup.ui.navigation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes


sealed class BottomBar(
    val title: String? = null,
    val route: String,
    @StringRes val titleResId: Int,
    @DrawableRes val icon: Int,
    @DrawableRes val iconFocused: Int
)
