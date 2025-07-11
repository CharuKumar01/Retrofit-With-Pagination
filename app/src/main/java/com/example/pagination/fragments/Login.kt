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
import com.example.pagination.databinding.FragmentLoginBinding

class Login : Fragment() {
    private lateinit var bind: FragmentLoginBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_register)
        }

        mainViewModel.loginSuccess.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_login_to_home2)
            }
        }

        mainViewModel.errorMessage.observe(viewLifecycleOwner) {
            Log.d("charu", it.toString())
        }

        bind.btnLogin.setOnClickListener {
            login()
        }

        mainViewModel.userFullName.observe(viewLifecycleOwner){
            Log.d("charu", "Full Name: $it")
        }
    }

    private fun login(){
        val email = bind.tfEmail.text.toString()
        val password = bind.tfPassword.text.toString()

        if(checkInputs()){
            mainViewModel.login(email, password)
        }
    }

    private fun checkInputs(): Boolean {
        return bind.tfEmail.text.toString().isNotEmpty() && bind.tfPassword.text.toString().isNotEmpty()
    }
}