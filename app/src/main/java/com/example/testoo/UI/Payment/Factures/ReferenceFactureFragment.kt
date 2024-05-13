package com.example.testoo.UI.Payment.Factures

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.testoo.R
import com.example.testoo.ViewModels.PaiementViewModel
import com.example.testoo.databinding.FragmentIamRechargeBinding
import com.example.testoo.databinding.FragmentReferenceFactureBinding


class ReferenceFactureFragment : Fragment() {

    private lateinit var binding: FragmentReferenceFactureBinding

    private val paiementViewModel by activityViewModels<PaiementViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentReferenceFactureBinding.inflate(layoutInflater)

        binding.button.setOnClickListener {
            val reference = binding.referenceET.text.toString()
            if(reference.isNotEmpty()){
                paiementViewModel.setReference(reference)
                Navigation.findNavController(binding.root).navigate(R.id.action_referenceFactureFragment_to_listFacturesFragment)
            }

        }

        return binding.root
    }


}
