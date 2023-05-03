package com.asmaa.goaltracker

import android.app.Application
import com.asmaa.goaltracker.data.FirebaseService
import com.asmaa.goaltracker.data.FirebaseServiceImpl

class GoalTrackerApplication : Application() {

    lateinit var firebaseService : FirebaseService
    override fun onCreate() {
        super.onCreate()
        firebaseService = FirebaseServiceImpl()
    }

}