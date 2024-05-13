package com.example.testoo.UI.Payment.Factures

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testoo.Domain.models.Compte
import com.example.testoo.Domain.models.Facture
import com.example.testoo.Domain.models.User
import com.example.testoo.R
import com.example.testoo.UI.Adapters.FactureListAdapter
import com.example.testoo.UI.Adapters.QuantityListener
import com.example.testoo.UI.Adapters.RechargeListAdapter
import com.example.testoo.ViewModels.PaiementViewModel
import com.example.testoo.ViewModels.UserViewModel
import com.example.testoo.databinding.FragmentListFacturesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ListFacturesFragment : Fragment() ,FactureListAdapter.OnFactureeClickListener{

    private val factures: ArrayList<Facture> = ArrayList<Facture>()
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val paiementViewModel by activityViewModels<PaiementViewModel>()

    private val userViewModel: UserViewModel by viewModels()

    private lateinit var binding: FragmentListFacturesBinding
    private var factureRecyclerView : RecyclerView? =null
    private var factureRecyclerViewAdapter : RecyclerView.Adapter<*>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentListFacturesBinding.inflate(layoutInflater)

        paiementViewModel.operatorTelecom.observe(viewLifecycleOwner, Observer{ operatorTelecom ->
            Toast.makeText(requireContext(),operatorTelecom, Toast.LENGTH_LONG).show()
        })
        paiementViewModel.reference.observe(viewLifecycleOwner, Observer{ reference ->
            binding.textReferenceContrat.setText("$reference")
        })
        paiementViewModel.domaine.observe(viewLifecycleOwner, Observer{ domaine ->
            Toast.makeText(requireContext(),domaine,Toast.LENGTH_LONG).show()
        })


        binding.button.setOnClickListener {
            paiementViewModel.setMontant(binding.textSommeFactures.text.toString())
            Navigation.findNavController(binding.root).navigate(R.id.action_listFacturesFragment_to_selectionCompteBancaireFragment)
        }



        return binding.root

    }

    private fun initFactureRecyclerView(factures: ArrayList<Facture>) {
        factureRecyclerViewAdapter = FactureListAdapter(factures)
        (factureRecyclerViewAdapter as FactureListAdapter).setOnFactureeClickListener(this@ListFacturesFragment)

        factureRecyclerView = binding.recyclerView

        factureRecyclerView?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        factureRecyclerView?.adapter = factureRecyclerViewAdapter

        binding.checkBoxToutSelectionner.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                (factureRecyclerViewAdapter as FactureListAdapter)?.checkAllItems(true)
                (factureRecyclerViewAdapter as FactureListAdapter)?.isAllItemsSelected = true
                var somme: Double = 0.00
                if (factures.isNotEmpty()) {
                    for (i in factures) {
                        i.montant?.let {
                            somme += it.toDouble()
                        }
                    }
                }
                somme += 5.50
                val formattedSomme = String.format("%.2f", somme)
                binding.textSommeFactures.text = "$formattedSomme"
                Toast.makeText(requireContext(), "Total sum: $formattedSomme", Toast.LENGTH_SHORT).show()
            } else if (!isChecked) {
                (factureRecyclerViewAdapter as FactureListAdapter)?.checkAllItems(false)
                (factureRecyclerViewAdapter as FactureListAdapter)?.isAllItemsSelected = false
                binding.textSommeFactures.text = "0"
            }
        }
    }




    private fun initFFactureRecyclerView(){
        val factures : ArrayList<Facture> = ArrayList<Facture>()
        factures.add(Facture("facture 1","1000",false))
        factures.add(Facture("facture 2","2000",false))
        factures.add(Facture("facture 3","3000",false))
        factures.add(Facture("facture 4","1000",false))
        factures.add(Facture("facture 5","1000",false))
        factures.add(Facture("facture 6","1000",false))
        factures.add(Facture("facture 7","1000",false))
        factures.add(Facture("facture 8","1000",false))

//        currentUser?.let {
//            user -> {
//            viewLifecycleOwner.lifecycleScope.launch {
//                val factures = withContext(Dispatchers.IO) {
//                    userViewModel.getFactureNonPayeForUserId(userId =user.uid )
////                userViewModel.getCompteForUserId(userId = user.uid)
//                }
//
//            }
//        }
//        }




        factureRecyclerViewAdapter = FactureListAdapter(factures)
//        (rechargeRecyclerViewAdapter as RechargeListAdapter).setOnRechargeClickListener(this@SelectionRechargeFragment)

        (factureRecyclerViewAdapter as FactureListAdapter).setOnFactureeClickListener(this@ListFacturesFragment)

        factureRecyclerView = binding.recyclerView



        factureRecyclerView?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        factureRecyclerView?.adapter = factureRecyclerViewAdapter

//        binding.checkBoxToutSelectionner.setOnClickListener {
//            (factureRecyclerView?.adapter as? FactureListAdapter)?.let { adapter ->
//                adapter.checkAllItems(binding.checkBoxToutSelectionner.isChecked)
//            }
//        }

        binding.checkBoxToutSelectionner.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                (factureRecyclerViewAdapter as FactureListAdapter)?.checkAllItems(true)
                (factureRecyclerViewAdapter as FactureListAdapter)?. isAllItemsSelected=true
                var somme: Double = 0.00
                if (factures.isNotEmpty()) {
                    for (i in factures) {
                        i.montant?.let {
                            somme += it.toDouble()
                        }
                    }
                }
                somme += 5.50
                val formattedSomme = String.format("%.2f", somme)
                binding.textSommeFactures.text = "$formattedSomme"
                Toast.makeText(requireContext(), "Total sum: $formattedSomme", Toast.LENGTH_SHORT).show()
            }
            else if (!isChecked){
                (factureRecyclerViewAdapter as FactureListAdapter)?.checkAllItems(false)
                (factureRecyclerViewAdapter as FactureListAdapter)?. isAllItemsSelected=false
                binding.textSommeFactures.text ="0"
            }

        }



//        binding.checkBoxToutSelectionner.setOnClickListener {
//
//            var somme: Double = 0.00
//            if (factures.isNotEmpty()) {
//                for (i in factures) {
//                    i.montant?.let {
//                        somme += it.toDouble()
//                    }
//                }
//            }
//            somme+=5.50
//            val formattedSomme = String.format("%.2f", somme)
//            binding.textSommeFactures.setText("$formattedSomme")
////        Toast.makeText(requireContext(), "${facture.nomFacture}", Toast.LENGTH_SHORT).show()
//            Toast.makeText(requireContext(), "Total sum: $formattedSomme", Toast.LENGTH_SHORT).show()
//
//        }



    }

//    override fun onQuantityChange(factures: ArrayList<Facture>) {
//        Toast.makeText(requireContext(),"${factures.toString()}",Toast.LENGTH_LONG).show()
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        initFactureRecyclerView()

        currentUser?.let { user ->
            val current_user=FirebaseDatabase.getInstance().reference
                .child("users")
                .child(currentUser.uid)

            current_user.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val user = snapshot.getValue(User::class.java)
                        val userName = user?.userName
                        binding.textCurrentUserName.setText(if (user?.userName.isNullOrEmpty()) "Nothing" else userName)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    println(error.toString())
                }
            })
        }


        currentUser?.let { user ->
            paiementViewModel.reference.observe(viewLifecycleOwner, Observer{ reference ->
                paiementViewModel.domaine.observe(viewLifecycleOwner, Observer{ domaine ->

                    viewLifecycleOwner.lifecycleScope.launch {
                        val factures = withContext(Dispatchers.IO) {
                            userViewModel.getFactureNonPayeForUserId(userId = user.uid, idContrat = "$reference",domaine=domaine)
                        }
                        initFactureRecyclerView(factures as ArrayList<Facture>)
                    }

                })
            })

        }

        if (binding.checkBoxToutSelectionner.isChecked) {
//            itemsFactureChecked.addAll(factures)
//            onFactureClicked(Facture("facture 1","1000"),factures)
        }
    }

    override fun onFactureClicked(facture: Facture,itemsFactureChecked: ArrayList<Facture>) {
        if((factureRecyclerViewAdapter as FactureListAdapter)?. isAllItemsSelected == true){
            (factureRecyclerViewAdapter as FactureListAdapter)?.checkAllItems(false)
            (factureRecyclerViewAdapter as FactureListAdapter)?. isAllItemsSelected=false
            binding.checkBoxToutSelectionner.isChecked = false
        }

//        (factureRecyclerViewAdapter as FactureListAdapter)?.checkAllItems(false)
//        (factureRecyclerViewAdapter as FactureListAdapter)?. isAllItemsSelected=false


//        var somme: Double = 0.00
//        factures.add(facture)
//        if (factures.isNotEmpty()) {
//            for (i in factures) {
//                i.montant?.let {
//                    somme += it.toDouble()
//                }
//            }
//        }
            var somme: Double = 0.00
//        factures.add(facture)
            if (itemsFactureChecked.isNotEmpty()) {
                for (i in itemsFactureChecked) {
                    i.montant?.let {
                        somme += it.toDouble()
                    }
                }
            }
            somme += 5.50
            val formattedSomme = String.format("%.2f", somme)
            binding.textSommeFactures.setText("$formattedSomme")
//        Toast.makeText(requireContext(), "${facture.nomFacture}", Toast.LENGTH_SHORT).show()
            Toast.makeText(requireContext(), "Total sum: $formattedSomme", Toast.LENGTH_SHORT)
                .show()
        }



}
