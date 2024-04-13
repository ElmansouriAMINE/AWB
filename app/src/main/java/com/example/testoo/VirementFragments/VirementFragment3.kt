package com.example.testoo.VirementFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.testoo.R
import com.example.testoo.ViewModels.VirementViewModel
import com.example.testoo.databinding.FragmentVirement2Binding
import com.example.testoo.databinding.FragmentVirement3Binding


class VirementFragment3 : Fragment() {

    private lateinit var binding: FragmentVirement3Binding
    private val virementViewModel by activityViewModels<VirementViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVirement3Binding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        virementViewModel.data.observe(viewLifecycleOwner, Observer { data ->
            Toast.makeText(requireContext(),data, Toast.LENGTH_LONG).show()
        })

        virementViewModel.data2.observe(viewLifecycleOwner, Observer { data2 ->
            Toast.makeText(requireContext(),data2+"", Toast.LENGTH_LONG).show()
        })
    }



}
