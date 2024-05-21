package com.example.testoo.UI.CardsManagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testoo.R
import com.example.testoo.databinding.FragmentChoixConfigCardBinding


class ChoixConfigCardFragment : Fragment() {

    private lateinit var binding: FragmentChoixConfigCardBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentChoixConfigCardBinding.inflate(layoutInflater)





        return binding.root
    }


}
