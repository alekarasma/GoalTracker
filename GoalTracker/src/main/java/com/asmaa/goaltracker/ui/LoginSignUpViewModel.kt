package com.asmaa.goaltracker.ui

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.asmaa.goaltracker.GoalTrackerApplication
import com.asmaa.goaltracker.data.FirebaseService
import com.asmaa.goaltracker.data.Response
import kotlinx.coroutines.*

class LoginSignUpViewModel(private val firebaseService: FirebaseService) : ViewModel() {
    private val TAG = "LoginSignUpVM"
    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading
    private var _inviteSuccessful = MutableLiveData<Boolean>()
    val registrationSuccess: LiveData<Boolean> get() = _inviteSuccessful
    var firebaseResponseMsg: String = ""

    fun registerUser(name: String, email: String, password: String) {
        var response : Response<String>  = Response.Error("", "")
        viewModelScope.launch {
            // display loading
            _loading.value = true
            //viewModel scope by default runs on main thread, run authenticate on IO thread
            val job = CoroutineScope(Dispatchers.IO).async {
                response = firebaseService.createAccount(email, password)

            }
            job.await()
            _loading.postValue(false)
            firebaseResponseMsg = when (response) {
                is Response.Success -> {
                    Log.e(TAG, "Success result")
                    _inviteSuccessful.postValue(true)
                    response.msg!!
                }
                is Response.Error -> {
                    Log.e(TAG, "UnSuccess result")
                    _inviteSuccessful.postValue(false)
                    response.msg!!
                }
            }
            Log.e(TAG, "Display result")
        }
    }

    fun firebaseResponse(): String {
        return firebaseResponseMsg
    }

    fun logInUser(email: String, password: String) {
        viewModelScope.launch {
            //display loading

            //viewModel scope by default runs on main thread, run authenticate on IO thread
            withContext(Dispatchers.IO)
            {
                firebaseService.authenticate(email, password)
            }
            //wait for result
            //stop display loading
            //display result
        }
    }

    fun signOut() {
        //viewModel scope by default runs on main thread, run signout on IO thread
        viewModelScope.launch(Dispatchers.IO) {
            firebaseService.signOut()
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return LoginSignUpViewModel(
                    (application as GoalTrackerApplication).firebaseService,
                ) as T
            }
        }
    }

}