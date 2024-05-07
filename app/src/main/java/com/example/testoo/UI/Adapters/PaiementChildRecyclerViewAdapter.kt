package com.example.testoo.UI.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.example.testoo.Domain.models.PaiementChildItem
import com.example.testoo.R

class PaiementChildRecyclerViewAdapter(private val childList: List<PaiementChildItem>,private val listener: OnChildItemClickListener) :
    RecyclerView.Adapter<PaiementChildRecyclerViewAdapter.ChildViewHolder>() {

    inner class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener  {
        val imageView: ImageView = itemView.findViewById(R.id.childLogoIv)
        val title: TextView = itemView.findViewById(R.id.childTitleTv)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onChildItemClick(childList[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.child_item_paiment, parent, false)
        return ChildViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val drawableResourceId = holder.itemView.resources.getIdentifier(
            childList[position].image,
            "drawable", holder.itemView.context.packageName
        )
        Glide.with(holder.itemView.context)
            .load(drawableResourceId)
            .transform(GranularRoundedCorners(30F, 30F, 0f, 0F))
            .into(holder.imageView)


//        holder.imageView.setImageResource(childList[position].image)

        holder.title.text = childList[position].title
    }

    override fun getItemCount(): Int {
        return childList.size
    }
    interface OnChildItemClickListener {
        fun onChildItemClick(item: PaiementChildItem)
    }
}
