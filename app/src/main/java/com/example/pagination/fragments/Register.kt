package com.example.pagination.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pagination.R
import com.example.pagination.ViewModel.MainViewModel
import com.example.pagination.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class Register : Fragment() {
    private lateinit var bind: FragmentRegisterBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            findNavController().navigate(R.id.action_register_to_home2)
        }

        bind.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_register_to_login)
        }

        mainViewModel.registrationSuccess.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_register_to_home2)
            }
        }

        mainViewModel.errorMessage.observe(viewLifecycleOwner) {
            Log.d("charu", it.toString())
        }

        bind.btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val fullName = bind.tfFullName.text.toString()
        val email = bind.tfEmail.text.toString()
        val password = bind.tfPassword.text.toString()

        if (checkInputs()) {
            mainViewModel.register(fullName, email, password)
        }
    }

    private fun checkInputs(): Boolean {
        return bind.tfFullName.text.toString().isNotEmpty() && bind.tfEmail.text.toString()
            .isNotEmpty() && bind.tfPassword.text.toString().isNotEmpty()
    }
}