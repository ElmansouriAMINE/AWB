package com.example.testoo.UI.CardsManagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testoo.R
import com.example.testoo.databinding.FragmentOppositionCarte2Binding
import com.example.testoo.databinding.FragmentOppositionCarteBinding


class OppositionCarteFragment2 : Fragment() {

    private lateinit var binding: FragmentOppositionCarte2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOppositionCarte2Binding.inflate(layoutInflater)




        return binding.root

    }


}
