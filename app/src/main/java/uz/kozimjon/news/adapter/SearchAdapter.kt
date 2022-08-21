package uz.kozimjon.news.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.kozimjon.news.R
import uz.kozimjon.news.databinding.AdapterRecommendedNewBinding
import uz.kozimjon.news.databinding.AdapterTopicBinding
import uz.kozimjon.news.model.NewsResponse


class SearchAdapter(val listener: OnSearchClickListener) : ListAdapter<NewsResponse.New, SearchAdapter.SearchViewHolder>(SearchDiffUtil()) {

    interface OnSearchClickListener {
        fun onSearchClick(new: NewsResponse.New)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = AdapterRecommendedNewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    // ViewHolder
    inner class SearchViewHolder(val binding: AdapterRecommendedNewBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: NewsResponse.New) {
            Glide.with(itemView.context).load(item.urlToImage).centerCrop().placeholder(R.drawable.ic_launcher_background).into(binding.ivImage)
            binding.tvName.text = item.source?.name ?: "No name"
            binding.tvCategoryName.text = item.author ?: "No category"

            itemView.setOnClickListener {
                listener.onSearchClick(item)
            }
        }
    }

    // DiffUtil
    class SearchDiffUtil : DiffUtil.ItemCallback<NewsResponse.New>() {
        override fun areItemsTheSame(oldItem: NewsResponse.New, newItem: NewsResponse.New): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: NewsResponse.New, newItem: NewsResponse.New): Boolean {
            return oldItem == newItem
        }
    }
}