package com.aknindustries.ecosearch.adaptors

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aknindustries.ecosearch.api.Records
import com.aknindustries.ecosearch.fragments.RecordsFragment

class UserRecordsAdaptor(
    private val context: Context,
    private val records: ArrayList<Records>,
    private val fragment: RecordsFragment,
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return records.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

}