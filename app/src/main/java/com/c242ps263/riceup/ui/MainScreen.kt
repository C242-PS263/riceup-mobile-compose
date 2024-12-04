package com.c242ps263.riceup.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.c242ps263.core.theme.RiceUpTheme
import com.c242ps263.riceup.disease.scanner.ScannerScreen
import com.c242ps263.riceup.ui.calendar.CalendarScreen
import com.c242ps263.riceup.ui.camera.CameraScreen
import com.c242ps263.riceup.ui.history.HistoryScreen
import com.c242ps263.riceup.ui.home.HomeScreen
import com.c242ps263.riceup.ui.navigation.BottomBar
import com.c242ps263.riceup.ui.navigation.model.BottomBar
import com.c242ps263.riceup.ui.navigation.model.BottomBarScreen
import com.c242ps263.riceup.ui.profile.ProfileScreen
import kotlin.collections.List

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val navigationItemList = arrayOf(
        BottomBarScreen.Home,
        BottomBarScreen.Calendar,
        BottomBarScreen.Camera,
        BottomBarScreen.History,
        BottomBarScreen.Profile
    )

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary,
        bottomBar = {
            // show and hide bottom navigation
            if (navigationItemList.map { it.route }.contains(currentDestination?.route)) {
                BottomBar(
                    modifier = modifier,
                    navController = navController,
                    navigationItemContentList = navigationItemList,
                    currentDestination = currentDestination
                )
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = BottomBarScreen.Home.route,
            modifier = Modifier.padding(it)
        ) {
            composable(BottomBarScreen.Home.route) {
                HomeScreen(

                )
            }
            composable(BottomBarScreen.Calendar.route) {
                CalendarScreen(

                )
            }
            composable(BottomBarScreen.Camera.route) {
                CameraScreen(

                )
            }
            composable(BottomBarScreen.History.route) {
                HistoryScreen(

                )
            }
            composable(BottomBarScreen.Profile.route) {
                ProfileScreen(

                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RiceUpTheme {
        MainScreen(

        )
    }
}