package com.sriram.smartdriveai.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.sriram.smartdriveai.ui.history.HistoryScreen
import com.sriram.smartdriveai.ui.trip.TripScreen

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {

        composable("main") {
            MainScreen(
                onStartTrip = {
                    navController.navigate("trip")
                },
                onViewHistory = {
                    navController.navigate("history")
                }
            )
        }

        composable("trip") {
            TripScreen()
        }

        composable("history") {
            HistoryScreen()
        }
    }
}
