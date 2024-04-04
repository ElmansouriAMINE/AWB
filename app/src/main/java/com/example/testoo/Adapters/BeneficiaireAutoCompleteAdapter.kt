package com.example.testoo.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.testoo.R
import com.example.testoo.models.User


class BeneficiaireAutoCompleteAdapter(context: Context, private val users: List<User>) :
    ArrayAdapter<User>(context, R.layout.beneficiaire_list, users) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.beneficiaire_list,
            parent,
            false
        )

        val user = getItem(position)
        view.findViewById<TextView>(R.id.textViewUserName).text = user?.userName.toString()
        view.findViewById<TextView>(R.id.textViewPhoneNumber).text = user?.phoneNumber
        view.findViewById<TextView>(R.id.textViewEmail).text = user?.email.toString()

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }
}

