package com.asmaa.goaltracker.data

interface FirebaseService {
    //val currentUserId: String
    suspend fun authenticate(email: String, password: String)
    suspend fun linkAccount(email: String, password: String)
    suspend fun deleteAccount()
    suspend fun signOut()
    suspend fun createAccount(email: String, password: String) : Response<String>
}