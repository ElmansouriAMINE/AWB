package com.example.testoo.UI.CardsManagement.TabLayouts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testoo.R
import com.example.testoo.databinding.FragmentPlafondsEtrangerBinding

class PlafondsEtrangerFragment : Fragment() {


    private lateinit var binding : FragmentPlafondsEtrangerBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentPlafondsEtrangerBinding.inflate(layoutInflater)

        return binding.root
    }


}
