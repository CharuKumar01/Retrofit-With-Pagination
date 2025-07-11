package com.example.pagination.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.pagination.R
import com.example.pagination.ViewModel.MainViewModel
import com.example.pagination.databinding.FragmentSearchBinding

class Search : Fragment() {
    private lateinit var bind: FragmentSearchBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.userFullName.observe(viewLifecycleOwner){
            bind.tvFullName.text = it.toString()
        }

        mainViewModel.getUserDetails()
    }
}