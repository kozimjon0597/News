package uz.kozimjon.news.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import uz.kozimjon.news.R
import uz.kozimjon.news.databinding.AdapterRecommendedNewBinding
import uz.kozimjon.news.databinding.AdapterTopicBinding
import uz.kozimjon.news.model.SavedNew
import uz.kozimjon.news.model.Topic


class TopicAdapter : ListAdapter<Topic, TopicAdapter.TopicAdapterViewHolder>(TopicDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicAdapterViewHolder {
        val view = AdapterTopicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopicAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopicAdapterViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    // ViewHolder
    inner class TopicAdapterViewHolder(val binding: AdapterTopicBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Topic) {
            Glide.with(itemView.context).load(item.image).centerCrop().placeholder(R.drawable.ic_launcher_background).into(binding.ivImage)
            binding.tvName.text = item.name ?: "No name"

            binding.llTopic.setOnClickListener {
                if (item.checked) {
                    binding.llTopic.setBackgroundColor(Color.parseColor("#F8F7F7"))
                    binding.tvName.setTextColor(Color.parseColor("#6E6D6D"))
                    item.checked = false
                } else {
                    binding.llTopic.setBackgroundColor(Color.parseColor("#475AD7"))
                    binding.tvName.setTextColor(Color.WHITE)
                    item.checked = true
                }
            }
        }
    }

    // DiffUtil
    class TopicDiffUtil : DiffUtil.ItemCallback<Topic>() {
        override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean {
            return oldItem == newItem
        }
    }
}