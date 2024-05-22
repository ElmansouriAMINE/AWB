package com.example.testoo.UI.CardsManagement.TabLayouts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testoo.R
import com.example.testoo.databinding.FragmentPlafondsMarocBinding


class PlafondsMarocFragment : Fragment() {

    private lateinit var binding :  FragmentPlafondsMarocBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentPlafondsMarocBinding.inflate(layoutInflater)


        return binding.root
    }


}
