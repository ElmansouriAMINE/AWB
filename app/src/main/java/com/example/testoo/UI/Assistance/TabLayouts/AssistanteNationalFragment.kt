package com.example.testoo.UI.Assistance.TabLayouts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testoo.R
import com.example.testoo.databinding.FragmentAssistanteNationalBinding


class AssistanteNationalFragment : Fragment() {

    private lateinit var binding : FragmentAssistanteNationalBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAssistanteNationalBinding.inflate(layoutInflater)




        return binding.root
    }


}
