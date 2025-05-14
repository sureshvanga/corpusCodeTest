package com.example.corpuscodetest.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.corpuscodetest.R
import com.example.corpuscodetest.databinding.ItemCarouselBinding
import com.example.corpuscodetest.databinding.ItemInnerBinding
import com.example.corpuscodetest.databinding.ItemRowBinding
import com.example.corpuscodetest.model.HomeItem
import com.example.corpuscodetest.model.HomeSection

class HomeAdapter: ListAdapter<HomeSection, RecyclerView.ViewHolder>(DIFF) {
    companion object {
        val DIFF = object : DiffUtil.ItemCallback<HomeSection>() {
            override fun areItemsTheSame(a: HomeSection, b: HomeSection) = a.title == b.title
            override fun areContentsTheSame(a: HomeSection, b: HomeSection) = a == b
        }
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position).contentType) {
        "CAROUSEL_AD" -> 0
        else -> 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> CarouselViewHolder(ItemCarouselBinding.inflate(inflater, parent, false))
            else -> RowViewHolder(ItemRowBinding.inflate(inflater, parent, false))
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val section = getItem(position)
        when (holder) {
            is CarouselViewHolder -> holder.bind(section.items)
            is RowViewHolder -> holder.bind(section)
        }
    }

    class CarouselViewHolder(private val binding: ItemCarouselBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(items: List<HomeItem>) {

            Glide.with(binding.imageView).load(items.firstOrNull()?.imageUrl).into(binding.imageView)
        }
    }

    class RowViewHolder(private val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(section: HomeSection) {
            binding.sectionTitle.text = section.title
            binding.innerRecycler.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            binding.innerRecycler.adapter = InnerAdapter(section.items)
        }
    }
}

class InnerAdapter(private val items: List<HomeItem>) : RecyclerView.Adapter<InnerAdapter.VH>() {
    class VH(val binding: ItemInnerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemInnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        if (item.imageUrl.isNotEmpty()) {
            Glide.with(holder.binding.imageView).load(item.imageUrl).into(holder.binding.imageView)
        } else {
            val label = holder.itemView.context.getString(R.string.label_id, item.id)
            holder.binding.textView.text = label

        }
    }
}
