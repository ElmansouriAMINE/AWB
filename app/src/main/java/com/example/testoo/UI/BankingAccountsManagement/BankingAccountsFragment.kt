package com.example.testoo.UI.BankingAccountsManagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testoo.R
import com.example.testoo.databinding.FragmentBankingAccountsBinding

class BankingAccountsFragment : Fragment() {

    private lateinit var binding: FragmentBankingAccountsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBankingAccountsBinding.inflate(layoutInflater)

































        // Inflate the layout for this fragment
        return binding.root
    }


}
