package com.example.testoo.UI.CardsManagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.testoo.R
import com.example.testoo.databinding.FragmentOppositionCarteBinding


class OppositionCarteFragment : Fragment() {

    private lateinit var binding: FragmentOppositionCarteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentOppositionCarteBinding.inflate(layoutInflater)

        binding.button.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_oppositionCarteFragment_to_oppositionCarteFragment2)
        }

        binding.backArrow.setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}
