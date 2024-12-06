package com.c242ps263.riceup.ui

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.c242ps263.core.theme.RiceUpTheme
import com.c242ps263.riceup.ui.splash.SplashScreen

@Composable
fun MainApp() {
    var showSplashScreen by remember {
        mutableStateOf(true)
    }

    if (showSplashScreen) {
        SplashScreen {
            showSplashScreen = false
        }
    } else {
        MainScreen()
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun MainAppPreview() {
    RiceUpTheme() {
        Surface {
            MainApp()
        }
    }
}