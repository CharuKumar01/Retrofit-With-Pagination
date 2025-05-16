package com.example.pagination.fragments.Home

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.pagination.R
import com.example.pagination.data.Jikan.Anime
import com.example.pagination.databinding.ItemAnimeBinding

class AnimeAdapter(private val context: Context) : ListAdapter<Anime, AnimeAdapter.AnimeViewHolder>(
    AnimeDiffCallBack()
) {

    inner class AnimeViewHolder(val bind: ItemAnimeBinding) : RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        return AnimeViewHolder(ItemAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val currentAnime = getItem(position)

        holder.bind.apply {
            val loadingPlaceholder =
                ContextCompat.getDrawable(context, R.drawable.loading_placeholder) as AnimationDrawable

            loadingPlaceholder.apply {
                setEnterFadeDuration(10)
                setExitFadeDuration(500)
                start()
            }

            Glide.with(animeImage.context)
                .load(currentAnime.images.jpg.image_url)
                .placeholder(loadingPlaceholder)
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .into(animeImage)

            tvTitle.text = currentAnime.title_english
            tvEpisodes.text = "Ep: ${currentAnime.episodes}"
            tvRating.text = "⭐ ${currentAnime.score}"

            llWatchTrailer.setOnClickListener {
                if (!currentAnime.trailer.url.isNullOrEmpty()) {
                    val youtubeIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:${currentAnime.trailer.youtube_id}"))
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(currentAnime.trailer.youtube_id))
                    try {
                        context.startActivity(youtubeIntent)
                    } catch (e: ActivityNotFoundException) {
                        context.startActivity(browserIntent)
                    }
                } else {
                    Toast.makeText(context, "Video not found!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

class AnimeDiffCallBack : DiffUtil.ItemCallback<Anime>() {
    override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean {
        return oldItem.mal_id == newItem.mal_id
    }

    override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean {
        return oldItem == newItem
    }

}