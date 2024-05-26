package com.example.testoo.UI.Adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.example.testoo.Data.Repository.UserRepositoryImpl
import com.example.testoo.Domain.models.Compte
import com.example.testoo.R
import com.example.testoo.Domain.models.Transaction
import com.example.testoo.Domain.models.User
import com.example.testoo.ViewModels.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class TransationListAdapter(items: ArrayList<Transaction>,userId: String, user :  User) :
    RecyclerView.Adapter<TransationListAdapter.Viewholder>() {

    val currentUser = FirebaseAuth.getInstance().currentUser
    var items: ArrayList<Transaction>
    var userId: String
    var user: User?
    var context: Context? = null

    fun updateList(newList: ArrayList<Transaction>) {
        items = newList
        notifyDataSetChanged()
    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
//        val binding: ItemTransactionBinding = ItemTransactionBinding.inflate(LayoutInflater.from(context), parent, false)

        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        context = parent.context
        return Viewholder(inflate)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
//        holder.textName.setText(items[position].receiverName)
//        holder.textMontant.text = "- ${items[position].montant} DH"
        holder.typeTransaction.setText(items[position].type)


                if (holder.typeTransaction.text == "Virement" && items[position].receiverName == user?.userName) {
                    holder.textName.setText(items[position].senderName)
                    Glide.with(holder.itemView.context)
                        .load(items[position].imgProfile)
                        .transform(GranularRoundedCorners(30F, 30F, 30f, 30F))
                        .into(holder.imageProfile)
                    holder.textMontant.text = "+ ${items[position].montant} DH"
                    holder.typeTransaction.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
                    holder.textMontant.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
                }
                else if (holder.typeTransaction.text == "Virement" && items[position].receiverName != user?.userName){
                    holder.textName.setText(items[position].senderName)
                    Glide.with(holder.itemView.context)
                        .load(items[position].imgProfile)
                        .transform(GranularRoundedCorners(30F, 30F, 30f, 30F))
                        .into(holder.imageProfile)
                    holder.textMontant.text = "- ${items[position].montant} DH"
                    holder.typeTransaction.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
                    holder.textMontant.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
                }

                else {
                    holder.textName.setText(items[position].receiverName)
                    val drawableResourceId = holder.itemView.resources.getIdentifier(
                        items[position].imgProfile,
                        "drawable", holder.itemView.context.packageName
                    )
                    Glide.with(holder.itemView.context)
                        .load(drawableResourceId)
                        .transform(GranularRoundedCorners(30F, 30F, 30f, 30F))
                        .into(holder.imageProfile)
                    holder.textMontant.text = "- ${items[position].montant} DH"
                    holder.typeTransaction.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
                    holder.textMontant.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.red))

                }


        holder.textDateHeure.text = "" + items[position].dateHeure

        holder.itemView.setOnClickListener { v: View? ->
           println("testing....")
            showTransactionDialog(holder.itemView.context, items[position])
        }
    }

    private fun showTransactionDialog(context: Context, transaction: Transaction) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_transaction_details, null)


        val imageProfile = dialogView.findViewById<ImageView>(R.id.dialog_imageProfile)
        val textName = dialogView.findViewById<TextView>(R.id.dialog_textName)
        val textMontant = dialogView.findViewById<TextView>(R.id.dialog_textMontant)
        val textTypeTransaction = dialogView.findViewById<TextView>(R.id.dialog_textTypeTransaction)
        val textDateHeure = dialogView.findViewById<TextView>(R.id.dialog_textDateHeure)
        val dialogClose = dialogView.findViewById<ImageView>(R.id.dialog_close)

//        textName.text = transaction.receiverName

        if(transaction.type == "Paiement" || transaction.type == "Retrait"){
            textMontant.text = "- ${transaction.montant} DH"
            textName.text = transaction.receiverName
        }
        else{
            textMontant.text = "+ ${transaction.montant} DH"
            textName.text = "Virement vers ${transaction.receiverName}"}

        textTypeTransaction.text = transaction.type
        textDateHeure.text = transaction.dateHeure

        val drawableResourceId = context.resources.getIdentifier(
            transaction.imgProfile,
            "drawable", context.packageName
        )
        if(drawableResourceId !=0){
            Glide.with(context)
                .load(drawableResourceId)
                .transform(GranularRoundedCorners(30F, 30F, 30f, 30F))
                .into(imageProfile)
        }
        else{
            Glide.with(context)
                .load(transaction.imgProfile)
                .transform(GranularRoundedCorners(30F, 30F, 30f, 30F))
                .into(imageProfile)

        }


        val dialog = androidx.appcompat.app.AlertDialog.Builder(context)
            .setView(dialogView)
            .create()

        dialogClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun getItemCount(): Int {
        return items.size
    }


    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textName: TextView
        var textDateHeure: TextView
        var textMontant: TextView
        var imageProfile: ImageView
        var typeTransaction: TextView

        init {
            imageProfile = itemView.findViewById(R.id.imageProfile)
            textName = itemView.findViewById(R.id.textName)
            textMontant = itemView.findViewById(R.id.textMontant)
            textDateHeure = itemView.findViewById(R.id.textDateHeure)
            typeTransaction = itemView.findViewById(R.id.textTypeTransaction)
        }
    }

    init {
        this.items = items
        this.userId =userId
        this.user=user
    }
}
