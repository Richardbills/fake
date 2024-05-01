package com.example.fitnesscoach

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.ByteArrayOutputStream
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class FbViewModel @Inject constructor(
    val auth: FirebaseAuth
) : ViewModel(){

    private var uid = auth.currentUser?.uid.toString()

    val signedIn = mutableStateOf(false)
    val isLogout = mutableStateOf(false)
    val inProgress = mutableStateOf(false)
    val popupNotification = mutableStateOf<Event<String>?>(null)
    var customData : User = User()

    fun register(email: String, pass: String,firstName: String, lastName: String) {
        inProgress.value = true

        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { it ->
                if(it.isSuccessful) {
                    // Set uid
                    uid = auth.currentUser?.uid.toString()

                    // Update profile
                    updateProfile(firstName)

                    // Save extra profile data
                    saveExtraProfileData(firstName, lastName, pass)
                }
                else {
                    inProgress.value = false
                    handleException(it.exception, "Registration failed")
                }
            }
    }

    fun saveExtraProfileData(firstName: String, lastName: String, pass: String) {
        // Save custom registration data
        val user = User(
            firstName,
            lastName
        )

        FirebaseAuth
            .getInstance()
            .currentUser
            ?.uid?.let { it1 ->
                FirebaseDatabase.getInstance()
                    .getReference("Users")
                    .child(
                        it1
                    )
                    .setValue(user)
                    .addOnCompleteListener { it ->
                        if (it.isSuccessful){
                            Log.d("TAG", "Saved custom profile record")

                            getCustomData()

                            // Attempt saving the password
                            if(pass.isNotEmpty()){
                               auth.currentUser
                                   ?.updatePassword(pass)
                                   ?.addOnCompleteListener {update ->
                                       if (update.isSuccessful){
                                           Log.d("TAG", "Password updated successfully")
                                       }
                                       else{
                                           Log.d("TAG", "Error updating password " + it.exception)
                                           handleException(it.exception, "Update failed")
                                       }
                                   }
                            }

                        }
                        else{
                            Log.d("TAG GD", "Not saving custom profile record" + it.exception)
                        }
                    }
            }
    }

   private fun updateProfile(firstName: String, uri : Uri ? = null){
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(firstName)
            // You can also set photo URL if needed
            .setPhotoUri(uri)
            .build()

        auth.currentUser?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { updateTask ->
                if (updateTask.isSuccessful) {
                    signedIn.value = true
                    inProgress.value = false
                    Log.d("TAG", "User profile updated.")
                } else {
                    // Failed to update display name
                    Log.w("TAG", "Failed to update profile.", updateTask.exception)
                }
            }
    }

    fun getCustomData() {
        FirebaseAuth.getInstance().currentUser?.uid?.let {
            FirebaseDatabase.getInstance().getReference("Users").child(it).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val dataSnapshot = task.result // Get the DataSnapshot
                        uid = it
                        Log.d("TAG UID", uid)
                        Log.d("TAG Err", dataSnapshot.toString())
                        if (dataSnapshot.exists()) {
                            val user = dataSnapshot.getValue(User::class.java)
                            if (user != null) {
                                // Save user custom data to model property
                                customData = user

                                val firstName = user.firstName
                                val lastName = user.lastName
                                Log.d("TAG Custom data", "FirstName: $firstName, LastName: $lastName")
                            } else {
                                Log.d("TAG Custom data", "User object for current user is null")
                            }
                        } else {
                            Log.d("TAG Custom data", "DataSnapshot does not exist for current user")
                        }
                    } else {
                        Log.d("TAG Custom data", "Failed to fetch data: ${task.exception}")
                    }
                }
        }
    }

    fun signInWithCustomToken(credential: String){
        inProgress.value = true

        auth.signInWithCustomToken(credential)
            .addOnCompleteListener{
                if (it.isSuccessful) {
                    signedIn.value = true
                    inProgress.value = false
                }
                else {
                    inProgress.value = false
                    handleException(it.exception, "Login failed")
                }
            }

    }

    fun signIn(email: String, pass: String) {
        inProgress.value = true

        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener{
                if (it.isSuccessful) {
                    signedIn.value = true
                    isLogout.value = false
                    inProgress.value = false

                    getCustomData()
                }
                else {
                    inProgress.value = false
                    handleException(it.exception, "Login failed")
                }
            }
    }

    fun uploadBitmapToFirebase(firstName: String, bitmap: Bitmap) {
        // Create a reference to the file you want to upload
        val imagesRef = FirebaseStorage
            .getInstance()
            .reference
            .child("images/${UUID.randomUUID()}.jpg")

        // Convert bitmap to byte array
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        // Upload the byte array to Firebase Storage
        val uploadTask = imagesRef.putBytes(data)

        // Listen for the upload task to complete
        uploadTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("TAG", "Image uploaded successfully")

                // Get the download URL for the uploaded image
                imagesRef.downloadUrl.addOnSuccessListener { uri ->
                    // Handle the download URL
                    Log.d("TAG", "Download URL: $uri")
                    updateProfile(firstName, uri)
                }
            } else {
                // Handle errors
                Log.e("TAG", "Failed to upload image: ${task.exception}")
            }
        }
    }

    fun signOut() {
        auth.signOut()

        signedIn.value = false
        inProgress.value = false
        isLogout.value = true
        Log.e("TAG", "Log out")
    }

    private fun handleException(exception: Exception? = null, customMessage: String = "") {
        exception?.printStackTrace()
        val  errorMsg = exception?.localizedMessage ?: ""
        val message = if (customMessage.isEmpty()) errorMsg
         else "$customMessage: $errorMsg"
        popupNotification.value = Event(message)
    }
}