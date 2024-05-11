package com.example.testoo.UI.Payment.Factures

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testoo.Domain.models.Facture
import com.example.testoo.R
import com.example.testoo.UI.Adapters.FactureListAdapter
import com.example.testoo.UI.Adapters.QuantityListener
import com.example.testoo.UI.Adapters.RechargeListAdapter
import com.example.testoo.databinding.FragmentListFacturesBinding


class ListFacturesFragment : Fragment() ,FactureListAdapter.OnFactureeClickListener{

    private val factures: ArrayList<Facture> = ArrayList<Facture>()

    private lateinit var binding: FragmentListFacturesBinding
    private var factureRecyclerView : RecyclerView? =null
    private var factureRecyclerViewAdapter : RecyclerView.Adapter<*>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentListFacturesBinding.inflate(layoutInflater)

        return binding.root

    }

    private fun initFactureRecyclerView(){
        val factures : ArrayList<Facture> = ArrayList<Facture>()
        factures.add(Facture("facture 1","1000"))
        factures.add(Facture("facture 2","2000"))
        factures.add(Facture("facture 3","3000"))
        factures.add(Facture("facture 4","1000"))
        factures.add(Facture("facture 5","1000"))
        factures.add(Facture("facture 6","1000"))
        factures.add(Facture("facture 7","1000"))
        factures.add(Facture("facture 8","1000"))

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
        initFactureRecyclerView()
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
