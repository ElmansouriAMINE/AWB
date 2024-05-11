package com.example.testoo.UI.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testoo.Domain.models.Facture
import com.example.testoo.R
import com.google.firebase.database.snapshot.BooleanNode

class FactureListAdapter(items: ArrayList<Facture>) :
    RecyclerView.Adapter<FactureListAdapter.Viewholder>() {
    var items: ArrayList<Facture>
    private var listener: FactureListAdapter.OnFactureeClickListener? = null
    var itemsFactureChecked: ArrayList<Facture> = ArrayList<Facture>()
    var isAllItemsSelected:Boolean = false
    var quantityListener:QuantityListener?=null
    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {

        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_facture, parent, false)
        context = parent.context
        return Viewholder(inflate)
    }

    interface OnFactureeClickListener{
        fun onFactureClicked(facture: Facture, itemsFactureChecked: ArrayList<Facture>)
    }
    fun checkAllItems(isChecked: Boolean) {
        if (isChecked) {
            itemsFactureChecked.addAll(items)
        } else {
            itemsFactureChecked.clear()
        }
        notifyDataSetChanged()
    }


    fun setOnFactureeClickListener(listener: OnFactureeClickListener){
        this.listener =listener
    }

    fun checkAllItems() {
        itemsFactureChecked.addAll(items)
        notifyDataSetChanged()
    }



    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.textNomFacture.setText(items[position].nomFacture)
        holder.textMontantFacture.text = "" + items[position].montant

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                itemsFactureChecked.add(items[position])

            } else {
                itemsFactureChecked.remove(items[position])
            }

        }
        holder.checkBox.isChecked = isAllItemsSelected //check all items with true

        if(items!=null && items.size > 0){

            if(holder.checkBox.isChecked){
                itemsFactureChecked.add(items[position])
            }else{
//                checkAllItems(false)
//                isAllItemsSelected=false
                itemsFactureChecked.remove(items[position])
            }
//            quantityListener?.onQuantityChange(itemsFactureChecked)

        }

        holder.checkBox.setOnClickListener {
            listener?.onFactureClicked(items[position],itemsFactureChecked)
//            checkAllItems(false)
//            isAllItemsSelected=false

            println("zoro")
        }

//        holder.itemView.setOnClickListener { v: View? ->
//            println("testing....")
//        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textNomFacture: TextView
        var textMontantFacture: TextView
        var checkBox: CheckBox

        init {
            textNomFacture = itemView.findViewById(R.id.textNomFacture)
            textMontantFacture = itemView.findViewById(R.id.TextMontantFacture)
            checkBox = itemView.findViewById(R.id.checkBox)

        }
    }

    init {
        this.items = items
    }
}

//class FactureListAdapter(items: ArrayList<Facture>) :
//    RecyclerView.Adapter<FactureListAdapter.Viewholder>() {
//    var items: ArrayList<Facture>
//    private var listener: FactureListAdapter.OnFactureeClickListener? = null
//    var itemsFactureChecked: ArrayList<Facture> = ArrayList<Facture>()
//    var itemsFactureCheckedd: ArrayList<Facture> = ArrayList<Facture>()
//    var quantityListener: QuantityListener? = null
//    var context: Context? = null
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
//        val inflate: View = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_facture, parent, false)
//        context = parent.context
//        return Viewholder(inflate)
//    }
//
//    interface OnFactureeClickListener {
//        fun onFactureClicked(facture: Facture, itemsFactureChecked: ArrayList<Facture>)
//    }
//
//    fun checkAllItems(isChecked: Boolean) {
//        if (isChecked) {
//            itemsFactureChecked.addAll(items)
//        } else {
//            itemsFactureChecked.clear()
//        }
//        notifyDataSetChanged()
//    }
//
//    fun setOnFactureeClickListener(listener: OnFactureeClickListener) {
//        this.listener = listener
//    }
//
//    override fun onBindViewHolder(holder: Viewholder, position: Int) {
//        val item = items[position]
//        holder.textNomFacture.text = item.nomFacture
//        holder.textMontantFacture.text = item.montant
//
//        holder.checkBox.isChecked = itemsFactureChecked.contains(item)
//
//        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//                itemsFactureChecked.add(item)
//            } else {
//                itemsFactureChecked.remove(item)
//            }
//        }
//
//        holder.itemView.setOnClickListener {
//            listener?.onFactureClicked(item, itemsFactureChecked)
//        }
//
//    }
//
//    override fun getItemCount(): Int {
//        return items.size
//    }
//
//    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var textNomFacture: TextView = itemView.findViewById(R.id.textNomFacture)
//        var textMontantFacture: TextView = itemView.findViewById(R.id.TextMontantFacture)
//        var checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
//    }
//
//    init {
//        this.items = items
//    }
//}
//
