package com.example.pagination

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pagination.data.Anime
import com.example.pagination.databinding.FragmentHomeBinding
import com.example.pagination.retrofit.JikanAPI
import com.example.pagination.retrofit.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Home : Fragment() {
    private lateinit var bind: FragmentHomeBinding

    private var isLoading = false
    private var currentPage = 1
    private lateinit var adapter: AnimeAdapter
    private val nextAnimeList = mutableListOf<Anime>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AnimeAdapter(requireActivity())
        val manager = LinearLayoutManager(requireContext())
        bind.apply {
            rvAnime.adapter = adapter
            adapter.submitList(emptyList())
            rvAnime.layoutManager = manager
            rvAnime.setHasFixedSize(true)
            isLoading = false
        }

        startFetchingAnime(1)
        onScrollListener()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun startFetchingAnime(page: Int) {
        val jikanAPI = RetrofitHelper.getInstance().create(JikanAPI::class.java)
        isLoading = true

        lifecycleScope.launch(Dispatchers.IO) {
            val response = jikanAPI.getTopAnime(page)
            if (response.isSuccessful) {
                val dataList = response.body()?.data?.filterNotNull() ?: emptyList()

                withContext(Dispatchers.Main) {
                    bind.apply {
                        nextAnimeList.addAll(dataList)
                        adapter.submitList(nextAnimeList)
                        Log.d("Debugging", "startFetchingAnime: $dataList")
                        adapter.notifyDataSetChanged()
                        isLoading = false
                        fetchingProgressBar.fadeOut()
                        progressBar.fadeOut()
                    }
                }
            }
        }
    }

    private fun onScrollListener() {
        bind.rvAnime.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = bind.rvAnime.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!isLoading && lastVisibleItem == layoutManager.itemCount - 1) {
                    Log.d("Debugging", "${!isLoading}, $currentPage, $lastVisibleItem, ${layoutManager.itemCount}")
                    bind.fetchingProgressBar.fadeIn()
                    isLoading = true
                    currentPage++
                    startFetchingAnime(currentPage)
                }
            }
        })
    }

    private fun View.fadeIn(duration: Long = 200) {
        if (visibility != View.VISIBLE) {
            animate().alpha(1f).setDuration(duration).withStartAction {
                visibility = View.VISIBLE
            }.start()
        }
    }

    private fun View.fadeOut(duration: Long = 200) {
        animate().alpha(0f).setDuration(duration).withEndAction {
            visibility = View.GONE
        }.start()
    }
}