package com.example.testoo.UI.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.example.testoo.Domain.models.PaiementChildItem
import com.example.testoo.Domain.models.PaiementParentItem
import com.example.testoo.Domain.models.Transaction
import com.example.testoo.R
import com.example.testoo.databinding.FragmentPaiementBinding
import com.example.testoo.databinding.ParentItemPaiementBinding

class PaiementParentRecyclerViewAdapter(
    private val parentItemList: ArrayList<PaiementParentItem>,
    private val onChildItemClickListener: PaiementChildRecyclerViewAdapter.OnChildItemClickListener
)
    : RecyclerView.Adapter<PaiementParentRecyclerViewAdapter.ViewHolder>(){

//    var parentItemList: ArrayList<PaiementParentItem>
    var context: Context? = null

//    inner class PaiementParentRecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
//        val parentImageView : ImageView = itemView.findViewById(R.id.parentLogoIv)
//        val parentTitle : TextView = itemView.findViewById(R.id.parentTitleTv)
//        val childRecyclerView : RecyclerView = itemView.findViewById(R.id.childRecyclerView)
//        val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.constraintLayout)
//
//    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.parent_item_paiement, parent, false)
        context = parent.context
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parentItem = parentItemList[position]

        holder.parentTitle.text = parentItem.title
        val drawableResourceId = holder.itemView.resources.getIdentifier(
            parentItemList[position].image,
            "drawable", holder.itemView.context.packageName
        )
        Glide.with(holder.itemView.context)
            .load(drawableResourceId)
            .into(holder.parentImageView)
//        holder.parentImageView.setImageResource(parentItem.image)
        holder.childRecyclerView.setHasFixedSize(true)
        holder.childRecyclerView.layoutManager = GridLayoutManager(holder.itemView.context,3)

        val adapter = PaiementChildRecyclerViewAdapter(parentItem.childItemList,onChildItemClickListener)
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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var parentImageView : ImageView
        var parentTitle : TextView
        var childRecyclerView : RecyclerView
        var constraintLayout: ConstraintLayout

        init {
            parentImageView = itemView.findViewById(R.id.parentLogoIv)
            parentTitle = itemView.findViewById(R.id.parentTitleTv)
            childRecyclerView = itemView.findViewById(R.id.childRecyclerView)
            constraintLayout = itemView.findViewById(R.id.constraintLayout)
        }
    }

//    init {
//        this.parentItemList = parentItemList
//    }

//    override fun onChildItemClick(item: PaiementChildItem) {
//
//        when(item.title){
//            "Orange" -> print("testAA")
//        }
//
//    }

    private fun getOptionsForChildItem(item: PaiementChildItem): List<String> {
        return when (item.title) {
            "Orange Recharge" -> listOf("Option 1", "Option 2", "Option 3")
            "Orange" -> listOf("Option A", "Option B", "Option C")
            // Add more cases for other child items if needed
            else -> emptyList() // Return an empty list if no options are available
        }
    }

//    override fun onChildItemClick(item: PaiementChildItem) {
//        // Debugging: Check if this method is called
//        Log.d("Adapter", "Child item clicked: ${item.title}")
//
//        // Perform your logic here based on the clicked child item
//        when (item.title) {
//            "Orange" -> {
//                // Debugging: Check if this block is executed
//                Log.d("Adapter", "Orange item clicked")
//                // Create a new RecyclerView for options
//
//                // Add optionsRecyclerView to the layout
////                binding.optionsLayout.addView(optionsRecyclerView)
//                // Your logic for Orange item click
//            }
//            // Add more cases as needed
//        }
//
//        print("crcr${getOptionsForChildItem(item)}")
//        print("lol")
//
//
//    }


}
