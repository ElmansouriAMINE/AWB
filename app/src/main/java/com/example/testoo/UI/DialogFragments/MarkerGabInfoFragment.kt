package com.example.testoo.UI.DialogFragments

import android.Manifest
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
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.testoo.Data.remote.Dto.AgenceWafaCashDto
import com.example.testoo.Data.remote.Dto.GabDto
import com.example.testoo.R
import com.example.testoo.databinding.FragmentMarkerGabInfoBinding
import com.example.testoo.databinding.FragmentMarkerInfoBinding


class MarkerGabInfoFragment : DialogFragment() {
    private var gab: GabDto? = null

    private lateinit var binding : FragmentMarkerGabInfoBinding

//    companion object {
//        private const val REQUEST_CALL_PHONE_PERMISSION = 123
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarkerGabInfoBinding.inflate(layoutInflater)
        val view = binding.root
        val nomAgence = binding.textNomAgence
        val textAdresse = binding.textAdresse
        val textDistance = binding.textDistance
        val arriverGps = binding.arriver

        gab?.let {
            nomAgence.text = it.nom
            textAdresse.text = it.adresse
            textDistance.text = it.distance
        }

        arriverGps.setOnClickListener {
            showNavigationOptions(gab!!)
        }

//        binding.appeler.setOnClickListener {
//            doCall(gab!!)
//        }

        binding.partager.setOnClickListener{
            doShareAgenceInfos(gab!!)
        }

        return view

    }

    private fun doShareAgenceInfos(gab: GabDto) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        val shareMessage = "Nom de Gab Attijariwafa Bank: ${gab.nom}\nAdresse: ${gab.adresse}\n"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        startActivity(Intent.createChooser(shareIntent, "Partager via"))
    }


//    private fun doCall(gab: GabDto){
//        gab?.let {
//            val phoneUri = Uri.parse("tel:${gab.fax}")
//            val callIntent = Intent(Intent.ACTION_CALL, phoneUri)
//            if (ContextCompat.checkSelfPermission(
//                    requireContext(),
//                    Manifest.permission.CALL_PHONE
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//                startActivity(callIntent)
//            } else {
//                requestPermissions(
//                    arrayOf(Manifest.permission.CALL_PHONE),
//                    REQUEST_CALL_PHONE_PERMISSION
//                )
//            }
//        }
//
//    }


//    private fun showNavigationOptions(gab: GabDto) {
//        val options = arrayOf("Google Maps", "Waze")
//        val builder = AlertDialog.Builder(requireContext())
//        builder.setTitle("Choisir une application de navigation")
//            .setItems(options) { dialog, which ->
//                when (which) {
//                    0 -> startNavigationWithGoogleMaps(gab)
//                    1 -> startNavigationWithWaze(gab)
//                }
//                dialog.dismiss()
//            }
//        builder.create().show()
//    }
//
//    private fun startNavigationWithGoogleMaps(gab: GabDto) {
//        val gmmIntentUri = Uri.parse("google.navigation:q=${gab.latitude},${gab.longitude}")
//        try {
//            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//            mapIntent.setPackage("com.google.android.apps.maps")
//            startActivity(mapIntent)
//        }catch (e: ActivityNotFoundException){
//            val builder = AlertDialog.Builder(requireContext())
//            builder.setTitle("Alerte")
//                .setMessage("Vous avez pas GoogleMaps installé sur votre telephone!!! Veuillez vérifier")
//            builder.create().show()
//        }
//    }
//
//    private fun startNavigationWithWaze(gab: GabDto) {
//        val wazeIntentUri = Uri.parse("https://waze.com/ul?q=${gab.latitude},${gab.longitude}")
//        try {
//            val mapIntent = Intent(Intent.ACTION_VIEW, wazeIntentUri)
//            mapIntent.setPackage("com.waze")
//            startActivity(mapIntent)
//        }catch (e: ActivityNotFoundException){
//            val builder = AlertDialog.Builder(requireContext())
//            builder.setTitle("Alerte")
//                .setMessage("Vous avez pas Waze installé sur votre telephone!!! Veuillez vérifier")
//            builder.create().show()
//
//        }
//
//    }
private fun showNavigationOptions(gab: GabDto) {
    val googleMapsInstalled = isAppInstalled("com.google.android.apps.maps")
    val wazeInstalled = isAppInstalled("com.waze")

    if (googleMapsInstalled && wazeInstalled) {
        showBothNavigationOptions(gab)
    } else if (googleMapsInstalled) {
        startNavigation(gab, "com.google.android.apps.maps")
    } else if (wazeInstalled) {
        startNavigation(gab, "com.waze")
    } else {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Aucune application de navigation trouvée")
            .setMessage("Veuillez installer Google Maps ou Waze pour utiliser cette fonctionnalité.")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }
}

    private fun showBothNavigationOptions(gab: GabDto) {
        val options = arrayOf("Google Maps", "Waze")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choisir une application de navigation")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> startNavigation(gab, "com.google.android.apps.maps")
                    1 -> startNavigation(gab, "com.waze")
                }
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun startNavigation(gab: GabDto, packageName: String) {
        val intentUri = if (packageName == "com.google.android.apps.maps") {
            Uri.parse("google.navigation:q=${gab.latitude},${gab.longitude}")
        } else {
            Uri.parse("https://waze.com/ul?q=${gab.latitude},${gab.longitude}")
        }

        try {
            val mapIntent = Intent(Intent.ACTION_VIEW, intentUri)
            mapIntent.setPackage(packageName)
            startActivity(mapIntent)
        } catch (e: ActivityNotFoundException) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Alerte")
                .setMessage("L'application de navigation sélectionnée n'est pas disponible sur votre téléphone.")
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            builder.create().show()
        }
    }

    private fun isAppInstalled(packageName: String): Boolean {
        return try {
            val packageManager = requireContext().packageManager
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }



    fun setGab(gab: GabDto) {
        this.gab = gab
    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == REQUEST_CALL_PHONE_PERMISSION) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////                doCall(gab!!)
//            } else {
//                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }



}

