package com.asmaa.goaltracker.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class FirebaseServiceImpl : FirebaseService {
    private val TAG = "FirebaseServiceImpl"

    override suspend fun createAccount(email: String, password: String): Response<String> {
        var firebaseUser: FirebaseUser
        var errorMsg = ""
        var response: Response<String> = Response.Error("", "")
        // Create an instance and create a register a user with email and password.
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Firebase registered user
                    firebaseUser = task.result!!.user!!
                    Log.i(TAG, "User registered successfully")
                    response = Response.Success("", "Successfully Registered")

                } else {
                    when (task.exception) {
                        is FirebaseAuthWeakPasswordException -> {
                            errorMsg = "Weak Password"
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            errorMsg = "Invalid Credentials"
                        }
                        is FirebaseAuthUserCollisionException -> {
                            errorMsg = "User already exist"
                        }
                    }
                    response = Response.Error(errorMsg, "Registration not successful")
                }
            }.await()
        return response
    }


    suspend fun loginUser(email: String, password: String): Response<String> {
        var errorMsg = ""
        var response: Response<String> = Response.Error("", "")
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i(TAG, "Successful Login")
                    response = Response.Success("", "Login Successful")

                } else {
                    when (task.exception) {

                    }
                    response = Response.Error(errorMsg, "Login not successful")
                }
            }.await()
        return response
    }

    override suspend fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }

    override suspend fun authenticate(email: String, password: String) {
        FirebaseAuth.getInstance().signOut()
    }

    override suspend fun linkAccount(email: String, password: String) {
        FirebaseAuth.getInstance().signOut()
    }

    override suspend fun deleteAccount() {
        FirebaseAuth.getInstance().signOut()
    }
}