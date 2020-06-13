package com.alpha2048.mergeadaptertest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.alpha2048.mergeadaptertest.databinding.ItemListGridItemBinding

class GridItemAdapter (private val viewModel: MainViewModel) : RecyclerView.Adapter<ItemGridViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemGridViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemListGridItemBinding.inflate(layoutInflater, parent, false)
        return ItemGridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemGridViewHolder, position: Int) {
        holder.binding.image.load(viewModel.repoItems[position].owner.avatar_url)
    }

    override fun getItemCount(): Int {
        return viewModel.repoItems.size
    }

}

class ItemGridViewHolder(val binding: ItemListGridItemBinding) : RecyclerView.ViewHolder(binding.root)