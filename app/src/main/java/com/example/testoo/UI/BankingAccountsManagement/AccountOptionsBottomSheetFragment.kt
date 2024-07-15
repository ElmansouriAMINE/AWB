package com.example.testoo.UI.BankingAccountsManagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.testoo.R
import com.example.testoo.databinding.FragmentAccountOptionsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AccountOptionsBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding:FragmentAccountOptionsBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.bottom_sheet_layout, container, false)


        val paymentLayout = view.findViewById<ConstraintLayout>(R.id.Payment)

        val virementLayout = view.findViewById<ConstraintLayout>(R.id.Virement)


//        paymentLayout.setOnClickListener {
//            Navigation.findNavController(view).navigate(R.id.toPaiementFragment)
//        }
//
//        virementLayout.setOnClickListener {
//            Navigation.findNavController(view).navigate(R.id.toVirementFragment)
//        }

        paymentLayout.setOnClickListener {
            findNavController().navigate(R.id.toPaiementFragment)

        }

        virementLayout.setOnClickListener {
            findNavController().navigate(R.id.toVirementFragment)
        }




        return view
    }
}
