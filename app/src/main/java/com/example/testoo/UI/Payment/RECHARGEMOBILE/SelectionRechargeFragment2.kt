package com.example.testoo.UI.Payment.RECHARGEMOBILE

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testoo.Domain.models.Compte
import com.example.testoo.R
import com.example.testoo.UI.Adapters.CompteListAdapterForPaiement
import com.example.testoo.Utils.BottomNavBarHandler
import com.example.testoo.ViewModels.PaiementViewModel
import com.example.testoo.databinding.FragmentSelectionRecharge2Binding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SelectionRechargeFragment2 : Fragment() , CompteListAdapterForPaiement.OnCompteClickListener {

    private lateinit var binding: FragmentSelectionRecharge2Binding
    private var compteRecyclerView : RecyclerView? =null
    private var compteRecyclerViewAdapter : RecyclerView.Adapter<*>? = null
    private val currentUser= FirebaseAuth.getInstance().currentUser
    private val paiementViewModel by activityViewModels<PaiementViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSelectionRecharge2Binding.inflate(layoutInflater)

        val circularProgressBar = binding.
        circularProgressBar.apply {
            progress = 33f
//            setProgressWithAnimation(25f, 1000)
            progressMax = 100f
            progressBarColor = Color.BLACK
            progressBarColorStart = Color.GRAY
            progressBarColorEnd = Color.YELLOW
            backgroundProgressBarColor = Color.GRAY
            backgroundProgressBarColorStart = Color.WHITE
            backgroundProgressBarColorEnd = Color.YELLOW
            roundBorder = true
        }

        circularProgressBar.setProgressWithAnimation(33f, 3000)

        lifecycleScope.launch {
            var progress = 33f
            while (progress <= 66f) {
                delay(60)
                progress += 1f
                circularProgressBar.setProgressWithAnimation(progress, 100)
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            val comptes = withContext(Dispatchers.IO) {
                currentUser?.let { paiementViewModel.getComptesForUserId(userId = it.uid) }
            }

            comptes?.let {

                compteRecyclerViewAdapter = CompteListAdapterForPaiement(comptes as ArrayList<Compte>)

                (compteRecyclerViewAdapter as CompteListAdapterForPaiement).setOnCompteClickListener(this@SelectionRechargeFragment2)

                compteRecyclerView = binding.viewRecycler

                compteRecyclerView?.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                compteRecyclerView?.adapter = compteRecyclerViewAdapter



            }
        }

        binding.buttonContinue.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_selectionRechargeFragment2_to_recapilatifPaiementFragment)
        }











        return binding.root
    }

    override fun onCompteClicked(compte: Compte) {
        println("this is the compte: $compte")

        paiementViewModel.apply {
            setCompteBancaire(compte)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? BottomNavBarHandler)?.setUpBottomNavBar()
    }


}
