package com.example.testoo.UI.NewEntreeRelation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.testoo.R
import com.example.testoo.ViewModels.EntreeRelationViewModel
import com.example.testoo.databinding.FragmentRenseignementBancaire2Binding


class RenseignementBancaireFragment2 : Fragment() {

    private lateinit var binding: FragmentRenseignementBancaire2Binding
    private val entreeRelationViewModel by activityViewModels<EntreeRelationViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentRenseignementBancaire2Binding.inflate(layoutInflater)

        binding.button.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.action_renseignementBancaireFragment2_to_renseigmentBankingCardsFragment)
        }





        return binding.root
    }


}
