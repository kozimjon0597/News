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
import uz.kozimjon.news.databinding.AdapterCategoryBinding
import uz.kozimjon.news.model.Category
import uz.kozimjon.news.model.Example
import uz.kozimjon.news.model.Topic

class CategoryAdapter(private val listener: OnCategoryClickListener) :
    ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(CategoryDiffUtil()) {

    interface OnCategoryClickListener {
        fun onCategoryClick(item: Category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(AdapterCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.onBind(getItem(position))

    }

    // ViewHolder
    inner class CategoryViewHolder(val binding: AdapterCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Category) {
            Glide.with(itemView.context).load(item.image).centerCrop().placeholder(R.drawable.ic_launcher_background).into(binding.ivImage)
            binding.tvName.text = item.name ?: "No name"

            itemView.setOnClickListener {
                listener.onCategoryClick(item)
            }
        }
    }

    // DiffUtil
    class CategoryDiffUtil : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }
}