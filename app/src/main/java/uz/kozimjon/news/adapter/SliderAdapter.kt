package uz.kozimjon.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import uz.kozimjon.news.databinding.AdapterSliderBinding


class SliderAdapter(private var list: ArrayList<Int>) :
    RecyclerView.Adapter<SliderAdapter.SliderAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderAdapterViewHolder {
        val view = AdapterSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SliderAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: SliderAdapterViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    // ViewHolder
    inner class SliderAdapterViewHolder(val binding: AdapterSliderBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Int) {
            binding.ivImage.setImageResource(item)

            binding.ivImage.setOnClickListener {
                Toast.makeText(itemView.context, item.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}