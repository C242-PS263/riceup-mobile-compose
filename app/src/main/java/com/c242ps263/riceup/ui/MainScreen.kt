package com.c242ps263.riceup.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.c242ps263.core.theme.RiceUpTheme
import com.c242ps263.riceup.data.model.Advice
import com.c242ps263.riceup.disease.data.model.DetectionDisease
import com.c242ps263.riceup.disease.ui.detection.DetectionScreen
import com.c242ps263.riceup.disease.ui.home.HomeScreen
import com.c242ps263.riceup.disease.ui.scanner.ScannerScreen
import com.c242ps263.riceup.ui.advice.AdviceScreen
import com.c242ps263.riceup.ui.history.HistoryScreen
import com.c242ps263.riceup.ui.navigation.BottomBar
import com.c242ps263.riceup.ui.navigation.model.BottomBarScreen
import com.c242ps263.riceup.ui.prediction.PredictionScreen
import com.c242ps263.riceup.ui.profile.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val navigationItemList = arrayOf(
        BottomBarScreen.Home,
        BottomBarScreen.Prediction,
        BottomBarScreen.Camera,
        BottomBarScreen.History,
    )

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.background,
                ),
                title = {
                    Text("RICE UP",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            )
        },
        bottomBar = {
            // show and hide bottom navigation
            if (navigationItemList.map { it.route }.contains(currentDestination?.route)) {
                BottomBar(
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
            composable(BottomBarScreen.Prediction.route) {
                PredictionScreen(

                )
            }
            composable(BottomBarScreen.Camera.route) {
                ScannerScreen(
                    navigateToPredictionResult = {
                        navController.navigate(it)
                    }
                )
            }
            composable<DetectionDisease> {
                DetectionScreen(
                    detectionDisease = it.toRoute(),
                    navigateToAdvice = { text ->
                        navController.navigate(Advice(text))
                    }
                )
            }
            composable<Advice> {
                AdviceScreen(
                    advice = it.toRoute()
                )
            }
            composable(BottomBarScreen.History.route) {
                HistoryScreen(

                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    RiceUpTheme {
        Surface {
            MainScreen()
        }
    }
}