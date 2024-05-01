package com.example.fitnesscoach.notification

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.fitnesscoach.FbViewModel

@Composable
fun NotificationMessage(vm: FbViewModel) {
    val notifyState = vm.popupNotification.value
    val notifyMessage = notifyState?.getContentOrNull()

    if(notifyMessage != null) {
        Toast.makeText(LocalContext.current, notifyMessage, Toast.LENGTH_SHORT).show()
    }
}