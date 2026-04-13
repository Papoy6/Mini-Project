package com.adam0006.miniproject.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("mainScreen")
    data object History : Screen("historyScreen")
}