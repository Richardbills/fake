package com.example.fitnesscoach.navigations.navgraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.fitnesscoach.FbViewModel
import com.example.fitnesscoach.navigations.Route
import com.example.fitnesscoach.navigations.Route.Home.ROOT_ROUTE
import com.example.fitnesscoach.pages.HomeScreen
import com.example.fitnesscoach.pages.ProfileScreen
import com.example.fitnesscoach.room.viewmodel.RecordsViewModel

fun NavGraphBuilder.mainApplicationNavGraph(
    navController: NavHostController,
    vm: FbViewModel,
    recordsViewModel: RecordsViewModel
){
    navigation(
        startDestination = Route.Home.route,
        route = ROOT_ROUTE
    ) {
        composable(
            route = Route.Home.route
        ) {
            HomeScreen(navController, vm, recordsViewModel)
        }
        composable(
            route = Route.Profile.route
        ) {
            ProfileScreen(navController, vm)
        }
    }
}
