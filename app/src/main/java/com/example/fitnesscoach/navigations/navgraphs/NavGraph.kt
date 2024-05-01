package com.example.fitnesscoach.navigations.navgraphs

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.fitnesscoach.FbViewModel
import com.example.fitnesscoach.navigations.Route.Home.LANDINGS_ROUTE
import com.example.fitnesscoach.navigations.Route.Home.ROOT_ROUTE
import com.example.fitnesscoach.notification.NotificationMessage
import com.example.fitnesscoach.room.viewmodel.RecordsViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SetupAuthGraph(
    navController: NavHostController,
    recordsViewModel: RecordsViewModel = viewModel()
) {
    val vm = hiltViewModel<FbViewModel>()
    val auth = FirebaseAuth.getInstance()
    NotificationMessage(vm = vm)

    var startWith = LANDINGS_ROUTE
    if (null != auth.currentUser) {
        startWith = ROOT_ROUTE
    }

    NavHost(
        navController = navController,
        startDestination = startWith,
    ) {

        // Landings graph
        landingsNavGraph(navController = navController)

        // Auth graph
        authNavGraph(navController = navController, vm = vm)

        // Main application graph
        mainApplicationNavGraph(navController = navController, vm = vm, recordsViewModel)

    }
}
