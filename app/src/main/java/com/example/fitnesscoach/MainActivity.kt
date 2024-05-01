package com.example.fitnesscoach

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fitnesscoach.ui.theme.FitnessCoachTheme
import com.example.fitnesscoach.navigations.navgraphs.SetupAuthGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition() { false }
        setContent {
            FitnessCoachTheme {
                navController = rememberNavController()

                SetupAuthGraph(navController)
            }
        }
    }
}