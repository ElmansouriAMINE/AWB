package com.example.testoo.UI.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.example.testoo.Domain.models.Recharge
import com.example.testoo.Domain.models.Transaction
import com.example.testoo.R

class RechargeListAdapter(items: ArrayList<Recharge>):
    RecyclerView.Adapter<RechargeListAdapter.Viewholder>(){
    private var listener: OnRechargeClickListener? = null
    private var lastClickedPosition: Int = -1
    var items: ArrayList<Recharge>
    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {

        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recharge, parent, false)
        context = parent.context
        return Viewholder(inflate)
    }

    interface OnRechargeClickListener{
        fun onRechargeClicked(recharge: Recharge)
    }

    fun setOnRechargeClickListener(listener: OnRechargeClickListener){
        this.listener =listener
    }





    override fun onBindViewHolder(holder: Viewholder, position: Int) {

//        holder.textRef.setText("Réf : "+items[position].ref)
//        holder.textRechargeType.text = "" + items[position].rechargeType
//        holder.textRechargeMontant.text = "" + items[position].montantRecharge+ " DH"
        val item = items[position]
        holder.textRef.text = "Réf : ${item.ref}"
        holder.textRechargeType.text = item.rechargeType
        holder.textRechargeMontant.text = "${item.montantRecharge} DH"
//        holder.itemView.setOnClickListener {
//            listener?.onRechargeClicked(item)
//            holder.isRechargeSelected.visibility=View.VISIBLE
//
//        }
//        holder.apply {
//            isRechargeSelected.visibility = if (position == lastClickedPosition) {
//                View.VISIBLE
//                holder.rechargeLayout.setBackgroundResource(R.drawable.recharge_selected_banner)
//
//            } else {
//                View.GONE
//            }
//            isColorSelected.visibility = if (position == lastClickedPosition) {
//                View.VISIBLE
//            } else {
//                View.GONE
//            }
//        }

        holder.apply {
            if (position == lastClickedPosition) {
                isRechargeSelected.visibility = View.VISIBLE
                rechargeLayout.setBackgroundResource(R.drawable.recharge_selected_banner)
            } else {
                isRechargeSelected.visibility = View.GONE
                rechargeLayout.setBackgroundResource(R.drawable.transaction_banner)
            }

//            isColorSelected.visibility = if (position == lastClickedPosition) {
//                View.VISIBLE
//            } else {
//                View.GONE
//            }
        }


        holder.itemView.setOnClickListener {
            if (lastClickedPosition != -1) {
                notifyItemChanged(lastClickedPosition)
            }
            lastClickedPosition = position
            notifyItemChanged(position)
            listener?.onRechargeClicked(item)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }


    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textRef : TextView
        var textRechargeType : TextView
        var textRechargeMontant : TextView

        var isRechargeSelected : ImageView

//        var isColorSelected : ConstraintLayout

        var rechargeLayout: ConstraintLayout



        init {
            textRef = itemView.findViewById(R.id.textRef)
            textRechargeType = itemView.findViewById(R.id.textTypeRecharge)
            textRechargeMontant = itemView.findViewById(R.id.textMontantRecharge)

            isRechargeSelected = itemView.findViewById(R.id.isRechargeSelected)
//            isColorSelected = itemView.findViewById(R.id.isColorSelected)

            rechargeLayout = itemView.findViewById(R.id.rechargeLayout)
        }

    }

    init {
        this.items = items
    }


}
