package com.example.testoo.UI.Adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.example.testoo.Domain.models.AccountItem

import com.example.testoo.R
import com.example.testoo.UI.BankingAccountsManagement.AccountOptionsBottomSheetFragment

//
//class ImageAdapterAccount : ListAdapter<AccountItem, ImageAdapterAccount.ViewHolder>(DiffCallback()) {
//
//    class DiffCallback : DiffUtil.ItemCallback<AccountItem>() {
//        override fun areItemsTheSame(oldItem: AccountItem, newItem: AccountItem): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: AccountItem, newItem: AccountItem): Boolean {
//            return oldItem == newItem
//        }
//    }
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val imageView = itemView.findViewById<ImageView>(R.id.imageView)
//        var textDateExipration: TextView = itemView.findViewById(R.id.textDateExpiration)
//        var textNumeroCarte: TextView = itemView.findViewById(R.id.textNumeroCarte)
//        var userName: TextView = itemView.findViewById(R.id.textUserName)
//        private val accountOptions = itemView.findViewById<ImageView>(R.id.accountOptions)
//
//        fun bindData(item: AccountItem) {
//            Glide.with(itemView)
//                .load(item.imageResId)
//                .transform(GranularRoundedCorners(30F, 30F, 0f, 0F))
//                .into(imageView)
//            textNumeroCarte.text = item.numero
//            textDateExipration.text = item.dateOuverture
//            userName.text = item.userId
//            accountOptions.setOnClickListener {
//                val bottomSheetFragment = AccountOptionsBottomSheetFragment()
//                bottomSheetFragment.show(fragmentActivity.supportFragmentManager, bottomSheetFragment.tag)
//
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(
//            LayoutInflater.from(parent.context)
//                .inflate(R.layout.account_item_layout, parent, false)
//        )
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val imageItem = getItem(position)
//        holder.bindData(imageItem)
//        holder.textNumeroCarte.setText(imageItem.numero)
//        holder.textDateExipration.setText(imageItem.dateOuverture)
//        holder.userName.setText(imageItem.solde.toString() +" DH")
//
//    }
//}




class ImageAdapterAccount(private val fragment:Fragment) : ListAdapter<AccountItem, ImageAdapterAccount.ViewHolder>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<AccountItem>() {
        override fun areItemsTheSame(oldItem: AccountItem, newItem: AccountItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AccountItem, newItem: AccountItem): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        var textDateExipration: TextView = itemView.findViewById(R.id.textDateExpiration)
        var textNumeroCarte: TextView = itemView.findViewById(R.id.textNumeroCarte)
        var userName: TextView = itemView.findViewById(R.id.textUserName)
        private val accountOptions = itemView.findViewById<ImageView>(R.id.accountOptions)

        fun bindData(item: AccountItem, fragment: Fragment) {
            Glide.with(itemView)
                .load(item.imageResId)
                .transform(GranularRoundedCorners(30F, 30F, 0f, 0F))
                .into(imageView)
            textNumeroCarte.text = item.numero
            textDateExipration.text = item.dateOuverture
            userName.text = item.userId
            accountOptions.setOnClickListener {
                val bottomSheetFragment = AccountOptionsBottomSheetFragment()
//                val bottomSheetFragment = AccountOptionsBottomSheetFragment()
                bottomSheetFragment.show(fragment.childFragmentManager, bottomSheetFragment.tag)
//                bottomSheetFragment.show(fragmentActivity.supportFragmentManager, bottomSheetFragment.tag)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.account_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageItem = getItem(position)
        holder.bindData(imageItem, fragment)
        holder.textNumeroCarte.text = imageItem.numero
        holder.textDateExipration.text = imageItem.dateOuverture
        holder.userName.text = "${imageItem.solde} DH"
    }
}
