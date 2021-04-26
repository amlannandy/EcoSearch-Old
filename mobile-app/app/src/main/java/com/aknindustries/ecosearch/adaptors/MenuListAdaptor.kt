@file:Suppress("DEPRECATION")

package com.aknindustries.ecosearch.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.aknindustries.ecosearch.R
import com.aknindustries.ecosearch.fragments.MenuFragment

class MenuListAdaptor(
    private val context: Context,
    private val menuItems: Array<String>,
    private val fragment: Fragment,
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
            R.layout.menu_list_item, parent, false
        ))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val title = menuItems[position]
        if (holder is MyViewHolder) {
            val titleTextView = holder.itemView.findViewById<TextView>(R.id.menu_list_item_title)
            val rightArrow = holder.itemView.findViewById<ImageView>(R.id.right_arrow_image)
            rightArrow.drawable.setTint(fragment.resources.getColor(R.color.colorDarkGrey))
            titleTextView.text = title
            holder.itemView.setOnClickListener {
                when (fragment) {
                    is MenuFragment -> fragment.menuItemOnClick(position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

}