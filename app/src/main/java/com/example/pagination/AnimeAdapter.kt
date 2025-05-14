package com.example.pagination

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pagination.data.Anime
import com.example.pagination.databinding.ItemAnimeBinding

class AnimeAdapter : ListAdapter<Anime, AnimeAdapter.AnimeViewHolder>(AnimeDiffCallBack()) {

    inner class AnimeViewHolder(val bind: ItemAnimeBinding) : RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        return AnimeViewHolder(ItemAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val currentAnime = getItem(position)
        holder.bind.apply {
            Glide.with(animeImage.context)
                .load(currentAnime.images.jpg.image_url)
                .into(animeImage)
        }
    }
}

class AnimeDiffCallBack : DiffUtil.ItemCallback<Anime>(){
    override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean {
        return oldItem.images == newItem.images
    }

    override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean {
        return oldItem == newItem
    }

}