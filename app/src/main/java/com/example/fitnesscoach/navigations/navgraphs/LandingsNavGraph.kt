package com.example.fitnesscoach.navigations.navgraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.fitnesscoach.FbViewModel
import com.example.fitnesscoach.navigations.Route
import com.example.fitnesscoach.navigations.Route.Home.LANDINGS_ROUTE
import com.example.fitnesscoach.pages.LandingPageActivity
import com.example.fitnesscoach.room.viewmodel.RecordsViewModel

// Landing Nav Graph
fun NavGraphBuilder.landingsNavGraph(
    navController: NavHostController
){
    navigation(
        startDestination = Route.LandingPage.route,
        route = LANDINGS_ROUTE
    ) {
        composable(
            // Show landing page if no account exist
            route = Route.LandingPage.route
        ) {
            LandingPageActivity(navController)
        }
    }
}
