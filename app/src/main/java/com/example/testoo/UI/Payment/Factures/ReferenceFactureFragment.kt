package com.example.testoo.UI.Payment.Factures

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.testoo.R
import com.example.testoo.databinding.FragmentIamRechargeBinding
import com.example.testoo.databinding.FragmentReferenceFactureBinding


class ReferenceFactureFragment : Fragment() {

    private lateinit var binding: FragmentReferenceFactureBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentReferenceFactureBinding.inflate(layoutInflater)

        binding.button.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_referenceFactureFragment_to_listFacturesFragment)
        }

        return binding.root
    }


}
