package com.example.testoo.UI.Payment.Factures

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testoo.Domain.models.Compte
import com.example.testoo.R
import com.example.testoo.UI.Adapters.CompteListAdapterForPaiement
import com.example.testoo.Utils.BottomNavBarHandler
import com.example.testoo.ViewModels.PaiementViewModel
import com.example.testoo.databinding.FragmentSelectionCompteBancaireBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SelectionCompteBancaireFragment : Fragment() , CompteListAdapterForPaiement.OnCompteClickListener {

    private lateinit var binding: FragmentSelectionCompteBancaireBinding
    private var compteRecyclerView : RecyclerView? =null
    private var compteRecyclerViewAdapter : RecyclerView.Adapter<*>? = null
    private val currentUser= FirebaseAuth.getInstance().currentUser
    private val paiementViewModel by activityViewModels<PaiementViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSelectionCompteBancaireBinding.inflate(layoutInflater)

//        paiementViewModel.montant.observe(viewLifecycleOwner, Observer{ montant ->
//            Toast.makeText(requireContext(),montant, Toast.LENGTH_LONG).show()
//        })

//        paiementViewModel.facturesClicked.observe(viewLifecycleOwner, Observer{ factures ->
//            Toast.makeText(requireContext(),factures.toString(), Toast.LENGTH_LONG).show()
//        })

        viewLifecycleOwner.lifecycleScope.launch {
            val comptes = withContext(Dispatchers.IO) {
                currentUser?.let { paiementViewModel.getComptesForUserId(userId = it.uid) }
            }

            comptes?.let {

                compteRecyclerViewAdapter = CompteListAdapterForPaiement(comptes as ArrayList<Compte>)

                (compteRecyclerViewAdapter as CompteListAdapterForPaiement).setOnCompteClickListener(this@SelectionCompteBancaireFragment)

                compteRecyclerView = binding.viewRecycler

                compteRecyclerView?.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                compteRecyclerView?.adapter = compteRecyclerViewAdapter



            }
        }

//        binding.buttonContinue.setOnClickListener {
//            Navigation.findNavController(binding.root).navigate(R.id.action_selectionCompteBancaireFragment_to_otpValidationFragment)
//        }


        return binding.root
    }

    override fun onCompteClicked(compte: Compte) {
        println("this is the compte: $compte")

        paiementViewModel.apply {
            setCompteBancaire(compte)
        }

        binding.buttonContinue.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_selectionCompteBancaireFragment_to_otpValidationFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? BottomNavBarHandler)?.setUpBottomNavBar()
    }


}
