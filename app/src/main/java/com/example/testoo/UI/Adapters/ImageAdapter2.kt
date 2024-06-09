package com.example.testoo.UI.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.example.testoo.Domain.models.ImageItem
import com.example.testoo.R

class ImageAdapter2(private val onItemClick: (ImageItem) -> Unit) : ListAdapter<ImageItem, ImageAdapter2.ViewHolder>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<ImageItem>() {
        override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(itemView: View, private val onItemClick: (ImageItem) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        var textDateExipration: TextView = itemView.findViewById(R.id.textDateExpiration)
        var textNumeroCarte: TextView = itemView.findViewById(R.id.textNumeroCarte)
        var userName: TextView = itemView.findViewById(R.id.textUserName)
        private val isCardOpposed = itemView.findViewById<View>(R.id.isCardChecked)

//        init {
//            itemView.setOnClickListener {
//                val position = adapterPosition
//                if (position != RecyclerView.NO_POSITION) {
//                    val currentItem = itemView.tag as ImageItem
////                    currentItem.isVisible = !currentItem.isVisible // Toggle visibility state
//////                    isCardOpposed.visibility = View.VISIBLE
////                    onItemClick((itemView.tag as ImageItem))
//                    currentItem.isChecked = !currentItem.isChecked // Toggle isChecked state
//                    isCardOpposed.visibility = if (currentItem.isChecked) View.VISIBLE else View.GONE
//                    onItemClick((itemView.tag as ImageItem))
//
//                }
//            }
//        }

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val currentItem = itemView.tag as ImageItem
                    currentItem.isChecked = !currentItem.isChecked
                    isCardOpposed.visibility = View.GONE
                    onItemClick((itemView.tag as ImageItem))
                }
            }
        }

        fun bindData(item: ImageItem) {
            itemView.tag = item
            val drawableResourceId = itemView.context.resources.getIdentifier(
                item.imageUrl, "drawable", itemView.context.packageName
            )
            Glide.with(itemView)
                .load(drawableResourceId)
                .transform(GranularRoundedCorners(30F, 30F, 0f, 0F))
                .into(imageView)
            textNumeroCarte.text = item.numeroCarte
            textDateExipration.text = item.dateExpiration
            userName.text = item.userName

            // Update the visibility based on isChecked
            isCardOpposed.visibility = if (item.isChecked) View.VISIBLE else View.GONE

            // Set onClickListener to toggle isChecked and update visibility
//            itemView.setOnClickListener {
//                val position = adapterPosition
//                if (position != RecyclerView.NO_POSITION) {
//                    val currentItem = itemView.tag as ImageItem
//                    currentItem.isChecked = !currentItem.isChecked
//                    isCardOpposed.visibility = View.GONE
//                    onItemClick(currentItem)
//                }
//            }







        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item_layout, parent, false)
        return ViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageItem = getItem(position)
        holder.bindData(imageItem)
    }
}
