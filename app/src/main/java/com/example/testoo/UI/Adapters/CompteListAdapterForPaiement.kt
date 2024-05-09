package com.example.testoo.UI.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testoo.Domain.models.Compte
import com.example.testoo.Domain.models.Recharge
import com.example.testoo.Domain.models.Transaction
import com.example.testoo.R


class CompteListAdapterForPaiement(items: ArrayList<Compte>):
    RecyclerView.Adapter<CompteListAdapterForPaiement.Viewholder>(){
    private var listener: OnCompteClickListener? = null
    private var lastClickedPosition: Int = -1
    var items: ArrayList<Compte>
    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {

        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.compte_item, parent, false)
        context = parent.context
        return Viewholder(inflate)
    }

    interface OnCompteClickListener{
        fun onCompteClicked(compte:Compte)
    }

    fun setOnCompteClickListener(listener: OnCompteClickListener){
        this.listener =listener
    }



    override fun onBindViewHolder(holder: Viewholder, position: Int) {

//        textViewCompteSolde  textViewCompteNumber   textViewCompteName

        val item = items[position]
        holder.soldeCompte.text = "${item?.solde.toString()+ " DH"}"
        holder.numeroCompte.text = item?.numero.toString()
        holder.nameCompte.text = "Compte"

        holder.isCompteSelected.visibility = if (position == lastClickedPosition) {
            View.VISIBLE
        } else {
            View.GONE
        }

        holder.itemView.setOnClickListener {
            if (lastClickedPosition != -1) {
                notifyItemChanged(lastClickedPosition)
            }
            lastClickedPosition = position
            notifyItemChanged(position)
            listener?.onCompteClicked(item)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }


    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var soldeCompte : TextView
        var numeroCompte : TextView
        var nameCompte : TextView
        var isCompteSelected : ImageView


        init {
            soldeCompte = itemView.findViewById(R.id.textViewCompteSolde)
            numeroCompte = itemView.findViewById(R.id.textViewCompteNumber)
            nameCompte = itemView.findViewById(R.id.textViewCompteName)
            isCompteSelected = itemView.findViewById(R.id.isCompteSelected)

        }

    }

    init {
        this.items = items
    }


}
