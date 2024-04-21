package com.example.testoo.UI.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.example.testoo.R
import com.example.testoo.Domain.models.Transaction


class TransationListAdapter(items: ArrayList<Transaction>) :
    RecyclerView.Adapter<TransationListAdapter.Viewholder>() {
    var items: ArrayList<Transaction>
    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
//        val binding: ItemTransactionBinding = ItemTransactionBinding.inflate(LayoutInflater.from(context), parent, false)

        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        context = parent.context
        return Viewholder(inflate)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.textName.setText(items[position].receiverName)
        holder.textMontant.text = "" + items[position].montant+"Dh"
        holder.textDateHeure.text = "" + items[position].dateHeure
        val drawableResourceId = holder.itemView.resources.getIdentifier(
            items[position].imgProfile,
            "drawable", holder.itemView.context.packageName
        )
        Glide.with(holder.itemView.context)
            .load(drawableResourceId)
            .transform(GranularRoundedCorners(30F, 30F, 0f, 0F))
            .into(holder.imageProfile)
        holder.itemView.setOnClickListener { v: View? ->
           println("testing....")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textName: TextView
        var textDateHeure: TextView
        var textMontant: TextView
        var imageProfile: ImageView

        init {
            imageProfile = itemView.findViewById(R.id.imageProfile)
            textName = itemView.findViewById(R.id.textName)
            textMontant = itemView.findViewById(R.id.textMontant)
            textDateHeure = itemView.findViewById(R.id.textDateHeure)
        }
    }

    init {
        this.items = items
    }
}
