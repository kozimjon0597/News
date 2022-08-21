package uz.kozimjon.news.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.kozimjon.news.R
import uz.kozimjon.news.databinding.AdapterCategoryOnHomeBinding
import uz.kozimjon.news.model.Category
import uz.kozimjon.news.model.CategoryOnHome
import uz.kozimjon.news.model.NewsResponse

class CategoryOnHomeAdapter(val listener: OnCategoryClickListener) : ListAdapter<CategoryOnHome, CategoryOnHomeAdapter.CategoryOnHomeViewHolder>(CategoryDiffUtil()) {

    interface OnCategoryClickListener {
        fun onCategoryClick(categoryOnHome: CategoryOnHome)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryOnHomeViewHolder {
        val view = AdapterCategoryOnHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryOnHomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryOnHomeViewHolder, position: Int) {
        holder.onBind(position)
    }

    // ViewHolder
    inner class CategoryOnHomeViewHolder(val binding: AdapterCategoryOnHomeBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val item = getItem(position)

            binding.tvName.text = item.name ?: "No name"

            if (item.checked) {
                binding.tvName.setBackgroundColor(Color.parseColor("#475AD7"))
                binding.tvName.setTextColor(Color.WHITE)
            } else {
                binding.tvName.setBackgroundColor(Color.parseColor("#F8F7F7"))
                binding.tvName.setTextColor(Color.parseColor("#6E6D6D"))
            }

            itemView.setOnClickListener {
                listener.onCategoryClick(item)
            }
        }
    }

    // DiffUtil
    class CategoryDiffUtil : DiffUtil.ItemCallback<CategoryOnHome>() {
        override fun areItemsTheSame(oldItem: CategoryOnHome, newItem: CategoryOnHome): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: CategoryOnHome, newItem: CategoryOnHome): Boolean {
            return oldItem == newItem
        }
    }
}