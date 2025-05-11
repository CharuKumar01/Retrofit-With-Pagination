package com.example.pagination

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pagination.data.Anime
import com.example.pagination.databinding.ItemAnimeBinding

class AnimeAdapter : RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {

    private var animeList: List<Anime> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Anime>) {
        this.animeList = list
        notifyDataSetChanged()
    }

    inner class AnimeViewHolder(val bind: ItemAnimeBinding) : RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val bind = ItemAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeViewHolder(bind)
    }

    override fun getItemCount(): Int {
        return animeList.size
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val currentAnime = animeList[position]
        holder.bind.apply {
            Glide.with(animeImage.context)
                .load(currentAnime.images.jpg.image_url)
                .into(animeImage)
        }
    }
}