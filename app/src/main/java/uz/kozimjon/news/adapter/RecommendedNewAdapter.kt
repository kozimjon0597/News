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
import uz.kozimjon.news.model.Category
import uz.kozimjon.news.model.NewsResponse


class RecommendedNewAdapter(private val listener: OnRecommendedNewListener) :
    ListAdapter<NewsResponse.New, RecommendedNewAdapter.RecommendedNewViewHolder>(NewDiffUtil()) {

    interface OnRecommendedNewListener {
        fun onRecommendedNewClick(new: NewsResponse.New)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedNewViewHolder {
        val view = AdapterRecommendedNewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendedNewViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendedNewViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    // ViewHolder
    inner class RecommendedNewViewHolder(val binding: AdapterRecommendedNewBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: NewsResponse.New) {
            Glide.with(itemView.context).load(item.urlToImage).centerCrop().placeholder(R.drawable.ic_launcher_background).into(binding.ivImage)
            binding.tvName.text = item.source?.name ?: "No name"
            binding.tvCategoryName.text = item.author ?: "No category"

            itemView.setOnClickListener {
                listener.onRecommendedNewClick(item)
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