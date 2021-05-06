package com.aknindustries.ecosearch.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aknindustries.ecosearch.databinding.ExploreListItemBinding
import com.aknindustries.ecosearch.fragments.HomeFragment
import com.aknindustries.ecosearch.models.Record
import com.aknindustries.ecosearch.utils.GlideLoader

class ExploreItemsAdaptor(
    private val context: Context,
    private val records: ArrayList<Record>,
    private val fragment: HomeFragment,
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ExploreListItemBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val record = records[position]
        if (holder is MyViewHolder) {
            val binding = holder.binding
            binding.root.setOnClickListener { fragment.goToRecordDetails(record.id) }
            GlideLoader(fragment.requireContext()).loadRecordImage(record.image, binding.imageViewAvatar)
        }
    }

    override fun getItemCount(): Int {
        return records.size
    }

    inner class MyViewHolder(val binding: ExploreListItemBinding) : RecyclerView.ViewHolder(binding.root)

}