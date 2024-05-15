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
        holder.textName.setText(items[position].receiverName)
//        holder.textMontant.text = "- ${items[position].montant} DH"
        holder.typeTransaction.setText(items[position].type)

//        val userName = currentUser?.let { getCurrentUser(it.uid) }

//        currentUser?.let { user ->
//
//            val current_user= FirebaseDatabase.getInstance().reference
//                .child("users")
//                .child(currentUser.uid)
//
//            current_user.addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    if (snapshot.exists()) {
//                        val user = snapshot.getValue(User::class.java)
//                        val userName = user?.userName
//                        if (holder.typeTransaction.text == "Virement") {
//                                Glide.with(holder.itemView.context)
//                                    .load(user?.photoUrl)
//                                    .into(holder.imageProfile)}
//                            holder.textMontant.text = "+ ${items[position].montant} DH"
//                            holder.typeTransaction.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
//                            holder.textMontant.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
//                        }
//                        else if (holder.typeTransaction.text == "Paiement") {
//                            holder.textMontant.text = "- ${items[position].montant} DH"
//                            holder.typeTransaction.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
//                            holder.textMontant.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
//
//                        }
//                        else {
//                            // Handle the case where snapshot does not exist
//                        }
//
//
//
//
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    // Gérer l'erreur ici
//                }
//            })
//        }

                if (holder.typeTransaction.text == "Virement" && items[position].receiverName == user?.userName) {
                    Glide.with(holder.itemView.context)
                        .load(user?.photoUrl)
                        .transform(GranularRoundedCorners(30F, 30F, 30f, 30F))
                        .into(holder.imageProfile)
                    holder.textMontant.text = "+ ${items[position].montant} DH"
                    holder.typeTransaction.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
                    holder.textMontant.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
                } else {
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




//        if(holder.typeTransaction.text == "Paiement"){
//            holder.typeTransaction.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
//        }
//        else {
//            holder.typeTransaction.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
//        }

        holder.textDateHeure.text = "" + items[position].dateHeure
//        val drawableResourceId = holder.itemView.resources.getIdentifier(
//            items[position].imgProfile,
//            "drawable", holder.itemView.context.packageName
//        )
//        Glide.with(holder.itemView.context)
//            .load(drawableResourceId)
//            .transform(GranularRoundedCorners(30F, 30F, 0f, 0F))
//            .into(holder.imageProfile)
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
