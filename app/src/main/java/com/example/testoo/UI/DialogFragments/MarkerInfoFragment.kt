package com.example.testoo.UI.DialogFragments

import android.Manifest.permission.CALL_PHONE
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.testoo.Data.remote.Dto.AgenceWafaCashDto
//import com.example.testoo.Manifest
import com.example.testoo.R
import com.example.testoo.databinding.FragmentMarkerInfoBinding
import java.lang.NullPointerException
import android.Manifest


class MarkerInfoFragment : DialogFragment() {
    private var agence: AgenceWafaCashDto? = null

    private lateinit var binding : FragmentMarkerInfoBinding

    companion object {
        private const val REQUEST_CALL_PHONE_PERMISSION = 123
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarkerInfoBinding.inflate(layoutInflater)
        val view = binding.root
        val nomAgence = binding.textNomAgence
        val textAdresse = binding.textAdresse
        val textHoraire1 = binding.textHoraire1
        val textHoraire4 = binding.textHoraire4
        val textHoraire6 = binding.textHoraire6
        val textFax = binding.textFax
        val textDistance = binding.textDistance
        val arriverGps = binding.arriver

        agence?.let {
            nomAgence.text = it.nom
            textAdresse.text = it.adresse
            textHoraire1.text = it.horaire1
            textHoraire4.text = it.horaire4
            textHoraire6.text = it.horaire6
            textFax.text = it.fax
            textDistance.text = it.distance
        }

        arriverGps.setOnClickListener {
            showNavigationOptions(agence!!)
        }

        binding.appeler.setOnClickListener {
            doCall(agence!!)
        }

        binding.partager.setOnClickListener{
            doShareAgenceInfos(agence!!)
        }

        return view

    }

    private fun doShareAgenceInfos(agence: AgenceWafaCashDto) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        val shareMessage = "Nom de l'agence WafaCash: ${agence.nom}\nAdresse: ${agence.adresse}\nHoraire: ${agence.horaire1}\nFax: ${agence.fax}"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        startActivity(Intent.createChooser(shareIntent, "Partager via"))
    }


    private fun doCall(agence:AgenceWafaCashDto){
        agence?.let {
            val phoneUri = Uri.parse("tel:${agence.fax}")
            val callIntent = Intent(Intent.ACTION_CALL, phoneUri)
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                startActivity(callIntent)
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.CALL_PHONE),
                    REQUEST_CALL_PHONE_PERMISSION
                )
            }
        }

    }


    private fun showNavigationOptions(agence:AgenceWafaCashDto) {
        val options = arrayOf("Google Maps", "Waze")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choisir une application de navigation")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> startNavigationWithGoogleMaps(agence)
                    1 -> startNavigationWithWaze(agence)
                }
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun startNavigationWithGoogleMaps(agence:AgenceWafaCashDto) {
        val gmmIntentUri = Uri.parse("google.navigation:q=${agence.latitude},${agence.longitude}")
        try {
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }catch (e:ActivityNotFoundException){
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Alerte")
                .setMessage("Vous avez pas GoogleMaps installé sur votre telephone!!! Veuillez vérifier")
            builder.create().show()
        }
    }

    private fun startNavigationWithWaze(agence:AgenceWafaCashDto) {
        val wazeIntentUri = Uri.parse("https://waze.com/ul?q=${agence.latitude},${agence.longitude}")
        try {
            val mapIntent = Intent(Intent.ACTION_VIEW, wazeIntentUri)
            mapIntent.setPackage("com.waze")
            startActivity(mapIntent)
        }catch (e: ActivityNotFoundException){
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Alerte")
                .setMessage("Vous avez pas Waze installé sur votre telephone!!! Veuillez vérifier")
            builder.create().show()

        }

    }


    fun setAgence(agence: AgenceWafaCashDto) {
        this.agence = agence
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CALL_PHONE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                doCall(agence!!)
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }



}

