package com.example.testoo.UI.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.example.testoo.Domain.models.PaiementParentItem
import com.example.testoo.R

class PaiementParentRecyclerViewAdapter(private val parentItemList : List<PaiementParentItem>)
    : RecyclerView.Adapter<PaiementParentRecyclerViewAdapter.PaiementParentRecyclerHolder>(){

    inner class PaiementParentRecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val parentImageView : ImageView = itemView.findViewById(R.id.parentLogoIv)
        val parentTitle : TextView = itemView.findViewById(R.id.parentTitleTv)
        val childRecyclerView : RecyclerView = itemView.findViewById(R.id.childRecyclerView)
        val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.constraintLayout)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PaiementParentRecyclerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.parent_item_paiement, parent , false)
        return PaiementParentRecyclerHolder(view)
    }

    override fun onBindViewHolder(holder: PaiementParentRecyclerHolder, position: Int) {
        val parentItem = parentItemList[position]

        holder.parentTitle.text = parentItem.title
//        val drawableResourceId = holder.itemView.resources.getIdentifier(
//            parentItem.image,
//            "drawable", holder.itemView.context.packageName
//        )
//        Glide.with(holder.itemView.context)
//            .load(drawableResourceId)
//            .transform(GranularRoundedCorners(30F, 30F, 0f, 0F))
//            .into(holder.parentImageView)
        holder.parentImageView.setImageResource(parentItem.image)
        holder.childRecyclerView.setHasFixedSize(true)
        holder.childRecyclerView.layoutManager = GridLayoutManager(holder.itemView.context,3)

        val adapter = PaiementChildRecyclerViewAdapter(parentItem.childItemList)
        holder.childRecyclerView.adapter = adapter

        val isExpandable = parentItem.isExpandable
        holder.childRecyclerView.visibility = if (isExpandable) View.VISIBLE else View.GONE


        holder.constraintLayout.setOnClickListener {

            isAnyItemExpanded(position)
            parentItem.isExpandable = !parentItem.isExpandable
            notifyItemChanged(position)
        }



    }

    private fun isAnyItemExpanded(position: Int) {

        val temp = parentItemList.indexOfFirst {
            it.isExpandable
        }

        if(temp >=0 && temp != position){
            parentItemList[temp].isExpandable = false
            notifyItemChanged(temp)
        }

    }

    override fun getItemCount(): Int {
        return parentItemList.size
    }


}
