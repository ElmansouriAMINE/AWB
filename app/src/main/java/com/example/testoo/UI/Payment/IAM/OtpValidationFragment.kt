package com.example.testoo.UI.Payment.IAM

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testoo.R
import com.example.testoo.databinding.FragmentOtpValidationBinding


class OtpValidationFragment : Fragment() {

    private lateinit var binding: FragmentOtpValidationBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtpValidationBinding.inflate(layoutInflater)



        return binding.root
    }


}
