package com.example.fitnesscoach.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitnesscoach.navigations.Route
import com.example.fitnesscoach.R

@Composable
fun AsyncImage(model: String, contentDescription: String) {

}

@Composable
fun LandingPageActivity(
    navController: NavController
) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.fitness_logo),
                    contentDescription = "Land picture",
                    modifier = Modifier.size(80.dp)
                )

                Text(
                    text = "FitCoach",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Text(
                    text = "The workout experience you get this way makes you feel like a personal fitness coach is right next to you, guiding you along your workout and helping you achieve your fitness goals.",
                    fontSize = 20.sp,
                    modifier = Modifier.alpha(.3f),
                    color = Color.Black
                )
            }


            Column(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.excercise),
                    contentDescription = "Land picture",
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        navController.navigate(Route.SignIn.route)
                    },
                    Modifier
                        .weight(1f)
                        .height(50.dp)
                ) {
                    Text(text = "Sign in")
                }

                Button(
                    onClick = {
                        navController.navigate(Route.Registration.route)
                    },
                    Modifier
                        .weight(1f)
                        .height(50.dp)
                ) {
                    Text(
                        text = "Register",
                        color = Color(red = 255, green = 125, blue = 0)
                    )

                }
            }
        }
}