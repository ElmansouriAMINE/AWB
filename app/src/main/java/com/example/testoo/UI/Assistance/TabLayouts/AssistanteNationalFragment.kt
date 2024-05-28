package com.example.testoo.UI.Assistance.TabLayouts

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.testoo.Data.remote.Dto.AgenceAttijariWafaDto
import com.example.testoo.R
import com.example.testoo.UI.DialogFragments.MarkerAttijariWafaInfoFragment
import com.example.testoo.databinding.FragmentAssistanteNationalBinding


class AssistanteNationalFragment : Fragment() {

    private lateinit var binding : FragmentAssistanteNationalBinding

    companion object {
        private const val REQUEST_CALL_PHONE_PERMISSION = 123
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAssistanteNationalBinding.inflate(layoutInflater)

        binding.button.setOnClickListener {
           doCall("0522588855")
        }




        return binding.root
    }

    private fun doCall(numero: String){
        numero?.let {
            val phoneUri = Uri.parse("tel:${numero}")
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
                    AssistanteNationalFragment.REQUEST_CALL_PHONE_PERMISSION
                )
            }
        }

    }


}
