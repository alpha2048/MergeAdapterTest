package com.alpha2048.mergeadaptertest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alpha2048.mergeadaptertest.databinding.ItemListBannerBinding

class BannerAdapter() : RecyclerView.Adapter<ItemBannerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemBannerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemListBannerBinding.inflate(layoutInflater, parent, false)
        return ItemBannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemBannerViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 1
    }
}

class ItemBannerViewHolder(val binding: ItemListBannerBinding) : RecyclerView.ViewHolder(binding.root)