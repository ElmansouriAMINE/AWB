package com.example.testoo.UI.Adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.example.testoo.Domain.models.AccountItem

import com.example.testoo.R


class ImageAdapterAccount : ListAdapter<AccountItem, ImageAdapterAccount.ViewHolder>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<AccountItem>() {
        override fun areItemsTheSame(oldItem: AccountItem, newItem: AccountItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AccountItem, newItem: AccountItem): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        var textDateExipration: TextView = itemView.findViewById(R.id.textDateExpiration)
        var textNumeroCarte: TextView = itemView.findViewById(R.id.textNumeroCarte)
        var userName: TextView = itemView.findViewById(R.id.textUserName)

        fun bindData(item: AccountItem) {
            Glide.with(itemView)
                .load(item.imageResId)
                .transform(GranularRoundedCorners(30F, 30F, 0f, 0F))
                .into(imageView)
            textNumeroCarte.text = item.numeroCarte
            textDateExipration.text = item.dateExpiration
            userName.text = item.userName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.account_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageItem = getItem(position)
        holder.bindData(imageItem)
        holder.textNumeroCarte.setText(imageItem.numeroCarte)
        holder.textDateExipration.setText(imageItem.dateExpiration)
        holder.userName.setText(imageItem.userName)
    }
}
