package com.example.pagination.fragments.Home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pagination.R
import com.example.pagination.data.Jikan.Anime
import com.example.pagination.databinding.FragmentHomeBinding
import com.example.pagination.retrofit.JikanAPI
import com.example.pagination.retrofit.TraceMoeAPI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class Home : Fragment() {
    private lateinit var bind: FragmentHomeBinding
    @Inject @Named("Jikan") lateinit var jikanAPI: JikanAPI
    @Inject @Named("TraceMoe") lateinit var traceMoeAPI: TraceMoeAPI

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

//        val selectImage = registerForActivityResult(
//            ActivityResultContracts.GetContent()
//        ){ uri: Uri? ->
//            val base64 = uriToBase64(requireContext(), uri!!).toString()
//            val encoded = URLEncoder.encode(base64, "UTF-8")
//            Log.d("Debugging", "onViewCreated: $encoded")
//            getAnimeDetail(encoded)
//        }
//        bind.tvUpload.setOnClickListener {
//            selectImage.launch("image/*")
//        }

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

        bind.cvSearch.setOnClickListener {
            findNavController().navigate(R.id.action_home2_to_search)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun startFetchingAnime(page: Int) {
        isLoading = true

        lifecycleScope.launch(Dispatchers.IO) {
            val response = jikanAPI.getTopAnime(page)
            if (response.isSuccessful) {
                val dataList = response.body()?.data?.filter {
                    it.title_english != null
                } ?: emptyList()

                withContext(Dispatchers.Main) {
                    bind.apply {
                        nextAnimeList.addAll(dataList)
                        adapter.submitList(nextAnimeList)
                        Log.d("Debugging", "startFetchingAnime: $dataList")
                        adapter.notifyDataSetChanged()
                        isLoading = false
                        fetchingProgressBar.fadeOut()
                        progressBar.fadeOut()
                        cvSearch.fadeIn()
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

//    private fun getAnimeDetail(uri: String){
//        lifecycleScope.launch(Dispatchers.IO) {
//            val response = traceMoeAPI.getAnimeDetails(uri)
//
//            if (response.isSuccessful){
//                Log.d("Debugging", "getAnimeDetail: ${response.body()}")
//            }
//        }
//    }

//    private fun uriToBase64(context: Context, uri: Uri): String? {
//        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
//        val bytes = inputStream.readBytes()
//        return Base64.encodeToString(bytes, Base64.NO_WRAP)
//    }
}