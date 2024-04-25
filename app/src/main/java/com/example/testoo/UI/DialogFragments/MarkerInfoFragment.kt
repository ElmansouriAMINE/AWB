package com.example.testoo.UI.DialogFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.testoo.Data.remote.Dto.AgenceWafaCashDto
import com.example.testoo.R


class MarkerInfoFragment : DialogFragment() {
    private var agence: AgenceWafaCashDto? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_marker_info, container, false)
        val nomAgence = view.findViewById<TextView>(R.id.textNomAgence)
        val textAdresse = view.findViewById<TextView>(R.id.textAdresse)
        val textHoraire1 = view.findViewById<TextView>(R.id.textHoraire1)
        val textHoraire4 = view.findViewById<TextView>(R.id.textHoraire4)
        val textHoraire6 = view.findViewById<TextView>(R.id.textHoraire6)
        val textFax = view.findViewById<TextView>(R.id.textFax)
        val textDistance = view.findViewById<TextView>(R.id.textDistance)

        agence?.let {
            nomAgence.text = it.nom
            textAdresse.text = it.adresse
            textHoraire1.text = it.horaire1
            textHoraire4.text = it.horaire4
            textHoraire6.text = it.horaire6
            textFax.text = it.fax
            textDistance.text = it.distance
        }
//        return inflater.inflate(R.layout.fragment_marker_info, container, false)
        return view

    }
    fun setAgence(agence: AgenceWafaCashDto) {
        this.agence = agence
    }


}
