package uz.kozimjon.news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.kozimjon.news.R
import uz.kozimjon.news.databinding.AdapterRecommendedNewBinding
import uz.kozimjon.news.model.Example
import uz.kozimjon.news.model.NewsResponse
import uz.kozimjon.news.model.SavedNew


class BookmarkAdapter(private val listener: OnBookmarkListener)
    : ListAdapter<SavedNew, BookmarkAdapter.BookmarkViewHolder>(BookmarkDiffUtil()) {

    interface OnBookmarkListener {
        fun onBookmarkClick(savedNew: SavedNew)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        return BookmarkViewHolder(AdapterRecommendedNewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    // ViewHolder
    inner class BookmarkViewHolder(val binding: AdapterRecommendedNewBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: SavedNew) {
            Glide.with(itemView.context).load(item.urlToImage).centerCrop().placeholder(R.drawable.ic_launcher_background).into(binding.ivImage)
            binding.tvName.text = item.name ?: "No name"
            binding.tvCategoryName.text = item.author ?: "No category"

            itemView.setOnClickListener {
                listener.onBookmarkClick(item)
            }
        }
    }

    // DiffUtil
    class BookmarkDiffUtil : DiffUtil.ItemCallback<SavedNew>() {
        override fun areItemsTheSame(oldItem: SavedNew, newItem: SavedNew): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SavedNew, newItem: SavedNew): Boolean {
            return oldItem == newItem
        }
    }
}