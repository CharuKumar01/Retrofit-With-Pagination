package com.example.pagination.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class MainViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val _registrationSuccess = MutableLiveData<Boolean>()
    val registrationSuccess: LiveData<Boolean> = _registrationSuccess

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    private val _userFullName = MutableLiveData<String>()
    val userFullName: LiveData<String> = _userFullName

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun register(fullName: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                val user = authResult.user
                val profileUpdate = UserProfileChangeRequest.Builder()
                    .setDisplayName(fullName)
                    .build()

                user?.updateProfile(profileUpdate)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _userFullName.postValue(user.displayName)
                            _registrationSuccess.postValue(true)
                        } else {
                            _errorMessage.postValue("Profile update failed: ${task.exception?.message}")
                        }
                    }
            }
            .addOnFailureListener {
                _errorMessage.postValue("Registration failed: ${it.message}")
            }
    }

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                val user = authResult.user
                _userFullName.value = user?.displayName
                val fullName = user?.displayName

                if (fullName.isNullOrEmpty()) {
                    _errorMessage.postValue("Login successful, but no name found.")
                } else {
                    _loginSuccess.postValue(true)
                }
            }
            .addOnFailureListener {
                _errorMessage.postValue("Login failed: ${it.message}")
            }
    }

    fun getUserDetails(){
        val user = auth.currentUser
        if(user != null){
            _userFullName.value = user.displayName
        }
    }
}

//Data Store, User Based It may be two different things but can be one topic too
//Storage system
//Error log handling with firebase