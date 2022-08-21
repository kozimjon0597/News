package uz.kozimjon.news.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.kozimjon.news.R
import uz.kozimjon.news.databinding.AdapterExampleBinding
import uz.kozimjon.news.model.Example

class ExampleAdapter: ListAdapter<Example, ExampleAdapter.ExampleViewHolder>(ExampleDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        return ExampleViewHolder(AdapterExampleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    // ViewHolder
    class ExampleViewHolder(val binding: AdapterExampleBinding): RecyclerView.ViewHolder(binding.root)  {

        fun onBind(example: Example) {
            binding.tvName.text = example.name
            binding.tvDesc.text = example.name
            Glide.with(itemView.context).load(example.image).centerCrop().placeholder(R.drawable.ic_launcher_background).into(binding.ivImage)
        }
    }

    // DiffUtil
    class ExampleDiffUtil : DiffUtil.ItemCallback<Example>() {
        override fun areItemsTheSame(oldItem: Example, newItem: Example): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Example, newItem: Example): Boolean {
            return oldItem == newItem
        }
    }
}