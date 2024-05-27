package com.example.testoo.UI.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.example.testoo.Domain.models.Assistance
import com.example.testoo.Domain.models.User
import com.example.testoo.R
import com.google.firebase.auth.FirebaseAuth



class AssistanceListAdapter(items: ArrayList<Assistance>) :
    RecyclerView.Adapter<AssistanceListAdapter.Viewholder>() {

    var items: ArrayList<Assistance>
    var context: Context? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_assistance, parent, false)
        context = parent.context
        return Viewholder(inflate)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.textNameCountry.setText(items[position].countryName)
        val drawableResourceId = holder.itemView.resources.getIdentifier(
            items[position].photoCountry,
            "drawable", holder.itemView.context.packageName
        )
        Glide.with(holder.itemView.context)
            .load(drawableResourceId)
            .transform(GranularRoundedCorners(30F, 30F, 30f, 30F))
            .into(holder.imageCountry)
        holder.textNumero.setText(items[position].assistancePhone)
        holder.itemView.setOnClickListener { v: View? ->
            println("testing....")

        }
    }



    override fun getItemCount(): Int {
        return items.size
    }


    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textNameCountry: TextView
        var imageCountry: ImageView
        var textNumero: TextView

        init {
            imageCountry = itemView.findViewById(R.id.imageCountry)
            textNameCountry = itemView.findViewById(R.id.textNameCountry)
            textNumero = itemView.findViewById(R.id.textNumero)
        }
    }

    init {
        this.items = items
    }
}
