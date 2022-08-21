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
import uz.kozimjon.news.databinding.AdapterNewBinding
import uz.kozimjon.news.model.Category
import uz.kozimjon.news.model.NewsResponse
import uz.kozimjon.news.model.SavedNew
import uz.kozimjon.news.paper_db.Favourites


class NewAdapter(private val onNewClickListener: OnNewClickListener) : ListAdapter<NewsResponse.New, NewAdapter.NewAdapterViewHolder>(NewDiffUtil()) {

    interface OnNewClickListener {
        fun onNewClick(new: NewsResponse.New)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewAdapterViewHolder {
        val view = AdapterNewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewAdapterViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    // ViewHolder
    inner class NewAdapterViewHolder(val binding: AdapterNewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: NewsResponse.New) {
            val new = SavedNew(item.source?.id, item.source?.name, item.author, item.urlToImage, item.description, item.content)
            if (Favourites.existFavourite(new)) {
                binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_fill)
            } else {
                binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_border)
            }

            Glide.with(itemView.context).load(item.urlToImage).centerCrop().placeholder(R.drawable.ic_launcher_background).into(binding.ivImage)
            binding.tvName.text = item.source?.name ?: "No name"
            binding.tvCategoryName.text = item.author ?: "No category"

            itemView.setOnClickListener {
                onNewClickListener.onNewClick(item)
            }

            binding.ivBookmark.setOnClickListener {
                if (Favourites.existFavourite(new)) {
                    binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_border)
                    Favourites.removeFavourite(new)
                } else {
                    binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_fill)
                    Favourites.addFavourite(new)
                }
            }
        }
    }

    // DiffUtil
    class NewDiffUtil : DiffUtil.ItemCallback<NewsResponse.New>() {
        override fun areItemsTheSame(oldItem: NewsResponse.New, newItem: NewsResponse.New): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: NewsResponse.New, newItem: NewsResponse.New): Boolean {
            return oldItem == newItem
        }
    }
}