package com.example.fitnesscoach.pages

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.fitnesscoach.FbViewModel
import com.example.fitnesscoach.R
import com.example.fitnesscoach.navigations.Route
import com.example.fitnesscoach.ui.theme.Purple80
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(
    navController : NavController,
    vm : FbViewModel = viewModel()
){
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val context = LocalContext.current

    // Form variables
    var  firstName by remember { mutableStateOf(vm.customData.firstName ?: "") }
    var  lastName by remember { mutableStateOf(vm.customData.lastName ?: "") }
    val  email by remember { mutableStateOf(user?.email ?: "") }
    var  password by remember { mutableStateOf("") }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var showDialog : Boolean = false

    if (null == auth.currentUser || vm.isLogout.value) {
        navController.popBackStack()
        navController.navigate(Route.Home.AUTHENTICATION_ROUTE)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ){uri: Uri? ->
        uri?.let{
          bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            }
            else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                ImageDecoder.decodeBitmap(source)
            }
            Log.d("TAG Bitmap", bitmap.toString())
        }
    }

    val cLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ){
        bitmap = it
    }

            Column (
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .background(Color.White),
            ){

                // Top Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ){

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){

                        Column(
                            modifier = Modifier.weight(2f)
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.fitness_logo),
                                contentDescription = "Application logo",
                                modifier = Modifier.size(90.dp)
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.End,
                            modifier = Modifier.clickable {
                                navController.popBackStack()
                            }
                        ){

                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Go back to previous page",
                                tint = Color.Black
                            )

                            Text(
                                text = "Go back",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }

                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Forms
                    Column (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        // Add profile image
                        if(null != auth.currentUser?.photoUrl && bitmap == null){
                            Image(
                                painter = rememberImagePainter(auth.currentUser?.photoUrl.toString()),
                                contentDescription = "Profile image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary)
                                    .size(150.dp)
                                    .border(
                                        width = 2.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = CircleShape
                                    )
                            )
                        }
                        else if (bitmap != null){
                            Image(
                                bitmap = bitmap?.asImageBitmap()!!,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary)
                                    .size(150.dp)
                                    .border(
                                        width = 2.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = CircleShape
                                    )
                                    .clickable { showDialog = true }
                            )
                        }
                        else {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_person_24),
                                contentDescription = null,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary)
                                    .size(150.dp)
                                    .border(
                                        width = 1.dp,
                                        color = Color.Blue,
                                        shape = CircleShape
                                    )
                                    .clickable { showDialog = true }
                            )
                        }
                        // End add profile image

                      Spacer(modifier = Modifier.height(20.dp))

//                        if(showDialog) {
                            // Image upload options
                            Row {
                                Column {
                                    Image(
                                        painter = painterResource(id = R.drawable.baseline_camera_24),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .size(50.dp)
                                            .clickable {
                                                cLauncher.launch()
                                                showDialog = false
                                            }
                                    )
                                    Text(
                                        text = "Camera",
                                        color = Color.White
                                    )
                                }
                                Column {
                                    Image(
                                        painter = painterResource(id = R.drawable.baseline_browse_gallery_24),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .size(50.dp)
                                            .clickable {
                                                launcher.launch("image/*")
                                                showDialog = true
                                            }
                                    )
                                }
                            }

//                      Spacer(modifier = Modifier.height(20.dp))

                        OutlinedTextField(
                            value = firstName,
                            label = { Text("First name") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.AccountCircle,
                                    contentDescription = "First name icon",
                                    tint = Purple80
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            onValueChange = { newText -> firstName = newText }
                        )

                        OutlinedTextField(
                            value = lastName,
                            label = { Text("Last name", color = Color.Black) },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.AccountCircle,
                                    contentDescription = "Lastname icon",
                                    tint = Purple80
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            onValueChange = { newText -> lastName = newText }
                        )

                        OutlinedTextField(
                            value = email,
                            label = { Text("Email", color = Color.Black) },
                            readOnly = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Next
                            ),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Email,
                                    contentDescription = "Email icon",
                                    tint = Purple80
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            onValueChange = {  }
                        )

                        OutlinedTextField(
                            value = password,
                            label = { Text("Password", color = Color.Black) },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Next
                            ),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Lock,
                                    contentDescription = "Password icon",
                                    tint = Purple80
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            onValueChange = { newText -> password = newText }
                        )

                        ElevatedButton(
                            shape = RectangleShape,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 15.dp),
                            colors = ButtonDefaults.buttonColors(Purple80),
                            onClick = {
                                Toast.makeText(context, "Update made successfull", Toast.LENGTH_SHORT).show()
                                vm.saveExtraProfileData(firstName, lastName, password)

                                bitmap?.let {
                                    vm.uploadBitmapToFirebase(firstName, it)
                                }

                            }
                        ) {Text(
                            text = "Update changes",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )}

                    }
                    // End of Forms

                }

            }
}
