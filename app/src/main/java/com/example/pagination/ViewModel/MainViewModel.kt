package com.example.pagination.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class MainViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val _registrationSuccess = MutableLiveData<Boolean>()
    val registrationSuccess: LiveData<Boolean> = _registrationSuccess

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun register(fullName: String, email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                val user = authResult.user
                val profileUpdate = UserProfileChangeRequest.Builder()
                    .setDisplayName(fullName)
                    .build()

                user?.updateProfile(profileUpdate)
                    ?.addOnCompleteListener {
                        if(it.isSuccessful){
                            _registrationSuccess.postValue(true)
                        }else{
                            _errorMessage.postValue("Profile update failed: ${it.exception?.message}")
                        }
                    }
            }
            .addOnFailureListener {
                _errorMessage.postValue("Registration failed: ${it.message}")
            }
    }
}