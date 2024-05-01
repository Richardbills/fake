package com.example.fitnesscoach.navigations.navgraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.fitnesscoach.FbViewModel
import com.example.fitnesscoach.navigations.Route
import com.example.fitnesscoach.navigations.Route.Home.AUTHENTICATION_ROUTE
import com.example.fitnesscoach.pages.LoginScreen
import com.example.fitnesscoach.pages.RegistrationScreen

// Auth Nav Graph
fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    vm: FbViewModel
){
    navigation(
        startDestination = Route.SignIn.route,
        route = AUTHENTICATION_ROUTE
    ){
        composable(
            route = Route.SignIn.route
        ){
            LoginScreen(navController, vm)
        }
        composable(
            route = Route.Registration.route
        ){
            RegistrationScreen(navController, vm)
        }
    }
}
