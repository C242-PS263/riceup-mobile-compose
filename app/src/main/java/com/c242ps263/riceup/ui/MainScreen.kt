package com.c242ps263.riceup.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
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
import com.c242ps263.riceup.disease.ui.history.HistoryScreen
import com.c242ps263.riceup.disease.ui.home.HomeScreen
import com.c242ps263.riceup.disease.ui.scanner.ScannerScreen
import com.c242ps263.riceup.ui.advice.AdviceScreen
import com.c242ps263.riceup.ui.navigation.BottomBar
import com.c242ps263.riceup.ui.navigation.model.BottomBar
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
        BottomBarScreen.Detection,
        BottomBarScreen.Profile,
    )
    val unavigationItemList = arrayOf(
        BottomBarScreen.History
    )
    val allNavTitle =
        (navigationItemList + unavigationItemList).map { it.route to it.title } + arrayOf(
            DetectionDisease::class.java.toString() to "HASIL"
        )
    val isBackButtonVisible by remember {
        derivedStateOf {
            navController.previousBackStackEntry != null
        }
    }

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    if (isBackButtonVisible) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.background,
                ),
                title = {
                    val navTitle =
                        allNavTitle.firstOrNull { it.first == currentDestination?.route }
                    Text(navTitle?.second ?: "RICE UP",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                actions = {
                    when (currentDestination?.route) {
                        BottomBarScreen.Detection.route -> {
                            BottomBarScreen.History.run {
                                IconButton(
                                    onClick = {
                                        navController.navigate(route)
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(id = icon),
                                        contentDescription = title,
                                        tint = MaterialTheme.colorScheme.background
                                    )
                                }
                            }
                        }
                    }
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
            modifier = Modifier
                .padding(it)
        ) {
            composable(BottomBarScreen.Home.route) {
                HomeScreen()
            }
            composable(BottomBarScreen.Prediction.route) {
                PredictionScreen()
            }
            composable(BottomBarScreen.Detection.route) {
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
                    },
                    navigateToHistory = {
                        navController.navigate(BottomBarScreen.History.route)
                    }
                )
            }
            composable<Advice> {
                AdviceScreen(
                    advice = it.toRoute()
                )
            }
            composable(BottomBarScreen.History.route) {
                HistoryScreen()
            }
            composable(BottomBarScreen.Profile.route) {
                ProfileScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    RiceUpTheme {
        MainScreen()
    }
}