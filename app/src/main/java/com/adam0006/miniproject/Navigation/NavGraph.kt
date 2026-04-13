package com.adam0006.miniproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adam0006.miniproject.Screen.MainScreen
import com.adam0006.miniproject.HistoryScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    val historyList = remember { mutableStateListOf<String>() }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController = navController) { dataBaru ->
                historyList.add(dataBaru)
            }
        }
        composable(route = Screen.History.route) {
            HistoryScreen(navController = navController, historyData = historyList)
        }
    }
}