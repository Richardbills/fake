package com.example.fitnesscoach.navigations

sealed class Route (val route: String) {

    // Constants
    val LANDINGS_ROUTE = "landing"
    val ONBOARDING_ROUTE = "onboarding"
    val AUTHENTICATION_ROUTE = "authentication"
    val ROOT_ROUTE = "root"


    // Landings
    data object LandingPage : Route(route = "LandingPageActivity")

    // Authentication
    data object SignIn : Route(route = "SignInActivity")
    data object Registration : Route(route = "RegistrationActivity")

    // Main/Root
    data object Home : Route(route = "HomeScreen")
    data object Profile : Route(route = "ProfileScreen")

}