package com.example.pagination

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.pagination.databinding.FragmentHomeBinding
import com.example.pagination.retrofit.JikanAPI
import com.example.pagination.retrofit.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Home : Fragment() {
    private lateinit var bind: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startFetchingAnime()
    }

    private fun startFetchingAnime() {
        val jikanAPI = RetrofitHelper.getInstance().create(JikanAPI::class.java)
        val adapter = AnimeAdapter()

        lifecycleScope.launch(Dispatchers.IO) {
            val response = jikanAPI.getTopAnime(2)
            if (response.isSuccessful) {
                val dataList = response.body()?.data ?: emptyList()

                withContext(Dispatchers.Main) {
                    bind.apply {
                        rvAnime.adapter = adapter
                        rvAnime.layoutManager = GridLayoutManager(requireContext(), 2)
                        adapter.submitList(dataList)
                    }
                }
            }
        }
    }
}