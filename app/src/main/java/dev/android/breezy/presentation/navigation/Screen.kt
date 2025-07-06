package dev.android.breezy.presentation.navigation

sealed class Screen(val route: String) {
    data object Onboarding : Screen("onboarding")
    data object LocationSetup : Screen("location_setup")
    data object CitySearch : Screen("city_search")
    data object Home : Screen("home")
}