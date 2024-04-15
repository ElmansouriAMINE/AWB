package com.example.testoo.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.testoo.R
import com.example.testoo.models.Compte

class CompteAutoCompleteAdapter(context: Context, private val comptes: List<Compte>) :
    ArrayAdapter<Compte>(context, R.layout.compte_list, comptes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.compte_list,
            parent,
            false
        )

        val compte = getItem(position)
        view.findViewById<TextView>(R.id.textViewCompteName).text = compte?.solde.toString()
        view.findViewById<TextView>(R.id.textViewCompteNumber).text = compte?.numero
        view.findViewById<TextView>(R.id.textViewCompteName).text = "Compte"

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }
}

