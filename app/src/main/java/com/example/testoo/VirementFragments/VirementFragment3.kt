package com.example.testoo.VirementFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testoo.R
import com.example.testoo.databinding.FragmentVirement2Binding
import com.example.testoo.databinding.FragmentVirement3Binding


class VirementFragment3 : Fragment() {

    private lateinit var binding: FragmentVirement3Binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVirement3Binding.inflate(layoutInflater)

        return binding.root
    }



}
