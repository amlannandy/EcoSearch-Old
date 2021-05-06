package com.aknindustries.ecosearch.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aknindustries.ecosearch.databinding.UserRecordListItemBinding
import com.aknindustries.ecosearch.fragments.RecordsFragment
import com.aknindustries.ecosearch.models.Record
import com.aknindustries.ecosearch.utils.GlideLoader

class UserRecordsAdaptor(
    private val context: Context,
    private val records: ArrayList<Record>,
    private val fragment: RecordsFragment,
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = UserRecordListItemBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val record = records[position]
        if (holder is MyViewHolder) {
            val binding = holder.binding
            binding.root.setOnClickListener { fragment.goToRecordDetails(record.id) }
            binding.userRecordListItemTitle.text = record.title
            binding.userRecordListItemLabel.text = "Unknown"
            binding.userRecordListItemDate.text = record.createdAt
            GlideLoader(context).loadRecordImage(record.image, binding.userRecordListItemImage)
        }
    }

    override fun getItemCount(): Int {
        return records.size
    }

    inner class MyViewHolder(val binding: UserRecordListItemBinding) : RecyclerView.ViewHolder(binding.root)

}