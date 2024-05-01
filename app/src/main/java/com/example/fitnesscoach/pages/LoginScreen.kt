package com.example.fitnesscoach.pages

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fitnesscoach.FbViewModel
import com.example.fitnesscoach.R
import com.example.fitnesscoach.navigations.Route
import com.example.fitnesscoach.navigations.Route.Home.ROOT_ROUTE

@Composable
fun LoginScreen(
    navController: NavController,
    vm: FbViewModel = viewModel()
) {
    val context = LocalContext.current
    val email = remember { mutableStateOf("") }
    val emailErrorState = remember { mutableStateOf(false) }
    val passwordErrorState = remember { mutableStateOf(false) }
    val password = remember { mutableStateOf("") }

    var passwordError by remember { mutableStateOf<String?>(null) }
    var passwordVisible by remember { mutableStateOf(false) }

    // Define a state to hold the response of the onClick event
    val signedInResponse by vm.signedIn

    // Display the registration response
    signedInResponse.let { response ->
        if(response){
            navController.navigate(ROOT_ROUTE)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Red)) { append("S") }
                withStyle(style = SpanStyle(color = Color.Black)) { append("ign") }
                withStyle(style = SpanStyle(color = Color.Red)) { append(" I") }
                withStyle(style = SpanStyle(color = Color.Black)) { append("n") }
            },
            fontSize = 30.sp
        )

        Spacer(Modifier.size(16.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = {
                if (emailErrorState.value) {
                    emailErrorState.value = false
                }
                email.value = it
            },
            isError = emailErrorState.value,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Enter Email*") }
        )

        if (emailErrorState.value) {
            Text(text = "Required", color = Color.Red)
        }

        Spacer(Modifier.size(16.dp))

        val passwordVisibility = remember { mutableStateOf(true) }

        OutlinedTextField(
            value = password.value,
            onValueChange = {
                if (passwordErrorState.value) {
                    passwordErrorState.value = false
                }
                password.value = it
            },
            isError = passwordErrorState.value,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Enter Password*") },
            trailingIcon = {
                Icon(
                    painter = painterResource(id =
                    if (passwordVisible) R.drawable.closed_eyes
                    else R.drawable.eye_close_up),
                    contentDescription = "Toggle visibility",
                    modifier = Modifier
                        .clickable { passwordVisible = !passwordVisible }
                        .padding(0.dp, 0.dp, 50.dp, 0.dp)
                        .size(20.dp),
                    tint = Color.Blue
                )
            },
            visualTransformation = if (passwordVisibility.value) PasswordVisualTransformation()
            else VisualTransformation.None
        )

        if (passwordErrorState.value) {
            Text(text = "Required", color = Color.Red)
        }

        Spacer(Modifier.size(16.dp))

        Button(onClick = {
            when {
                email.value.isEmpty() -> {
                    emailErrorState.value = true
                }
                password.value.isEmpty() -> {
                    passwordErrorState.value = true
                }
                else -> {
                    // Login
                    vm.signIn(email.value, password.value)

                    // Other settings
                    passwordErrorState.value = false
                    emailErrorState.value = false
                    Toast.makeText(context, "Logged in successfully", Toast.LENGTH_SHORT).show()
                }
            }
        }) {
            Text(text = "Login")
        }

        Text(
            text = "You do not have an account? Sign up now",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable {
                navController.navigate(Route.Registration.route)
            },
            color = Color.Black
        )
    }
}
