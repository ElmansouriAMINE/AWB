package com.example.testoo.UI.AgencesFragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.testoo.Data.remote.Dto.AgenceWafaCashDto
import com.example.testoo.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomInfomMarkerAdapter(private val context: Context,private val agence : AgenceWafaCashDto) : GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(marker: Marker): View? {
         return null
    }

    override fun getInfoWindow(marker: Marker): View? {
        val infoView = LayoutInflater.from(context).inflate(R.layout.custom_info_marker, null)
        val nomAgence = infoView.findViewById<TextView>(R.id.textNomAgence)
        val textAdresse = infoView.findViewById<TextView>(R.id.textAdresse)
        val textHoraire1 = infoView.findViewById<TextView>(R.id.textHoraire1)
        val textHoraire4 = infoView.findViewById<TextView>(R.id.textHoraire4)
        val textHoraire6 = infoView.findViewById<TextView>(R.id.textHoraire6)
        val textFax = infoView.findViewById<TextView>(R.id.textFax)
        val textDistance = infoView.findViewById<TextView>(R.id.textDistance)

        nomAgence.text = agence.nom
        textAdresse.text = agence.adresse
        textHoraire1.text = agence.horaire1
        textHoraire4.text= agence.horaire4
        textHoraire6.text= agence.horaire6
        textFax.text=agence.fax
        textDistance.text =agence.distance

        infoView.setOnTouchListener { _, _ -> true }

        return infoView

    }
}
