package com.example.fitnesscoach.pages

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.text.Layout
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.fitnesscoach.ActivityList
import com.example.fitnesscoach.FbViewModel
import com.example.fitnesscoach.R
import com.example.fitnesscoach.navigations.Route
import com.example.fitnesscoach.navigations.Route.Home.AUTHENTICATION_ROUTE
import com.example.fitnesscoach.retrofit_impl.model.RetrofitViewModel
import com.example.fitnesscoach.room.viewmodel.RecordsViewModel
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("CheckResult")
@Composable
fun HomeScreen(
    navController : NavController,
    vm : FbViewModel = viewModel(),
    recordsViewModel: RecordsViewModel = viewModel()
){
    vm.getCustomData()

    val auth = FirebaseAuth.getInstance()
    val uid = auth.currentUser?.uid ?: ""

    if (null == auth.currentUser || vm.isLogout.value) {
        navController.navigate(AUTHENTICATION_ROUTE)
    }

            Column (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                if (vm.inProgress.value) {
                    CircularProgressIndicator()
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.Top
            ) {
                //Top
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Box {
                        Image(
                            painter = painterResource(id = R.drawable.angle),
                            contentDescription = null,
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth(),
                            alignment = Alignment.TopEnd
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Column(
                                    modifier = Modifier.weight(2f)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.fitness_logo),
                                        contentDescription = "logo",
                                        modifier = Modifier.size(90.dp)
                                    )
                                }

                                Column(
                                    horizontalAlignment = Alignment.End,
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .clickable {
                                            navController.navigate(Route.SignIn.route)
                                            vm.signOut()
                                        }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.ExitToApp,
                                        contentDescription = "Log out user account",
                                        tint = Color.Black
                                    )

                                    Text(
                                        text = "Log out",
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                            }

                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.Start
                            ) {

                                Column(
                                    modifier = Modifier.weight(4f)
                                ) {
                                    Text(
                                        text = "Hi, ",
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        style = TextStyle(fontSize = 24.sp)
                                    )
                                    Text(
                                            text = if(null != auth.currentUser)
                                                if(null != vm.customData.firstName) vm.customData.firstName
                                                else auth.currentUser!!.displayName.toString()
                                            else "Buddy",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    style = TextStyle(fontSize = 24.sp)
                                    )
                                }

                                Column(
                                    horizontalAlignment = Alignment.End
                                ) {
                                    if(null != auth.currentUser?.photoUrl)
                                    {
                                        Image(
                                            painter = rememberImagePainter(auth.currentUser?.photoUrl.toString()),
                                            contentDescription = "Go to profile",
                                            modifier = Modifier
                                                .clip(CircleShape)
                                                .size(70.dp)
                                                .border(
                                                    width = 2.dp,
                                                    color = MaterialTheme.colorScheme.primary,
                                                    shape = CircleShape
                                                )
                                                .background(Color.White)
                                                .clickable {
                                                    navController.navigate(Route.Profile.route)
                                                }
                                        )
                                    }
                                    else{
                                        Image(
                                            painter = painterResource(id = R.drawable.baseline_person_24),
                                            contentDescription = "Go to profile",
                                            modifier = Modifier
                                                .clip(CircleShape)
                                                .size(70.dp)
                                                .border(
                                                    width = 2.dp,
                                                    color = MaterialTheme.colorScheme.primary,
                                                    shape = CircleShape
                                                )
                                                .background(Color.White)
                                                .clickable {
                                                    navController.navigate(Route.Profile.route)
                                                }
                                        )
                                    }
                                }

                            }

                        }
                    }
                }

                //Bottom
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(3f)
                        .verticalScroll(rememberScrollState())
                ){

                    Column {
                        Image(
                            painter = painterResource(id = R.drawable.main_image),
                            contentDescription = "image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(170.dp)
                                .background(Color.LightGray)
                        )
                    }

                    Column (
                        modifier = Modifier
                            .padding(start = 20.dp, 30.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "Popular news",
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(fontSize = 24.sp),
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                      ScrollableNewsRow()
                    }


                    Column (
                        modifier = Modifier
                            .padding(start = 20.dp, 30.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "Activities",
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(fontSize = 24.sp),
                            color = Color.Black
                        )

                        Text(
                            text = "Below are the sport activities you can use to train yourself in other to lose weight and be in shape.",
                            style = TextStyle(fontSize = 17.sp),
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        ExerciseList()
                    }



                }
    }
}

@Composable
fun ScrollableNewsRow(viewModel: RetrofitViewModel = viewModel()) {
    val data by viewModel.harryPotterData.observeAsState()
    val news = data?.articles

    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val context = LocalContext.current
        news?.forEach{

            ElevatedCard (
                modifier = Modifier
                    .height(130.dp)
                    .width(180.dp)
                    .padding(0.dp, 0.dp, 10.dp)
                    .clickable {
                        val uri = Uri.parse(it.url)
                        val intent = Intent(Intent.ACTION_VIEW, uri)

                        context.startActivity(intent)
                    }
            ){

                Text(
                    text = it.title.take(26),
                    modifier = Modifier.padding(10.dp)
                )

                Text(
                    text = it.source.name,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )
            }

        }
    }
}


@Composable
fun ExerciseList() {
    val lists = ActivityList.activityList
    for (list in lists) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 0.dp, 15.dp)
                .clickable {
                    //
                }
        ) {
            Row {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Image(
                        painter = painterResource(id = list.imageResId),
                        contentDescription = "Excercise image",
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(80.dp)
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            )
                            .background(Color.White),
                        contentScale = ContentScale.Fit,
                    )
                }
                Column (
                    modifier = Modifier
                        .weight(5f)
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Center
                ){
                    Text(
                        text = list.name,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = list.duration,
                        color = Color.Black
                    )
                }
            }
        }
    }
}