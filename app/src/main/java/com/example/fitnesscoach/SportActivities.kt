package com.example.fitnesscoach

import androidx.annotation.DrawableRes

data class SportActivities(
    @DrawableRes val imageResId: Int,
    val name: String,
    val duration: String
)

object ActivityList {
    val activityList = listOf(
        SportActivities(R.drawable.exercise1, "Exercise 1", "2: 50 seconds"),
        SportActivities(R.drawable.exercise2, "Exercise 2", "3: 50 seconds"),
        SportActivities(R.drawable.exercise3, "Exercise 3", "1: 50 seconds"),
        SportActivities(R.drawable.exercise4, "Exercise 4", "5: 50 seconds")
    )
}
