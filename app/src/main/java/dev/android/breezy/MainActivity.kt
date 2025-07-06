package dev.android.breezy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.android.breezy.presentation.city.screen.CitySearchScreen
import dev.android.breezy.presentation.home.screen.HomeScreen
import dev.android.breezy.presentation.location.screen.LocationSetupScreen
import dev.android.breezy.presentation.navigation.Screen
import dev.android.breezy.presentation.onboarding.screen.OnboardingScreen
import dev.android.breezy.ui.theme.BreezyTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BreezyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BreezyApp()
                }
            }
        }
    }
}

@Composable
fun BreezyApp() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = hiltViewModel()
    val startDestination by mainViewModel.startDestination.collectAsState()

    startDestination?.let { destination ->
        NavHost(
            navController = navController,
            startDestination = destination
        ) {
            composable(Screen.Onboarding.route) {
                OnboardingScreen(
                    onNavigateToLocationSetup = {
                        navController.navigate(Screen.LocationSetup.route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.LocationSetup.route) {
                LocationSetupScreen(
                    onNavigateToHome = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.LocationSetup.route) { inclusive = true }
                        }
                    },
                    onNavigateToCitySearch = {
                        navController.navigate(Screen.CitySearch.route)
                    }
                )
            }

            composable(Screen.CitySearch.route) {
                CitySearchScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateToHome = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.LocationSetup.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Home.route) {
                HomeScreen()
            }
        }
    }
}