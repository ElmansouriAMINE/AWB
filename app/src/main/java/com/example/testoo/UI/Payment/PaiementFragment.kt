package com.example.testoo.UI.Payment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testoo.Domain.models.PaiementChildItem
import com.example.testoo.Domain.models.PaiementParentItem
import com.example.testoo.R
import com.example.testoo.UI.Adapters.OptionsAdapter
import com.example.testoo.UI.Adapters.PaiementChildRecyclerViewAdapter
import com.example.testoo.UI.Adapters.PaiementParentRecyclerViewAdapter
import com.example.testoo.UI.Payment.IAM.IamRechargeFragment
import com.example.testoo.UI.VirementFragments.VirementFragment2
import com.example.testoo.Utils.BottomNavBarHandler
import com.example.testoo.ViewModels.PaiementViewModel
import com.example.testoo.databinding.FragmentPaiementBinding


class PaiementFragment : Fragment(), PaiementChildRecyclerViewAdapter.OnChildItemClickListener, OptionsAdapter.OnOptionClickListener {

    private lateinit var binding: FragmentPaiementBinding
//    private lateinit var parentRecyclerView: RecyclerView
//    private lateinit var parentList : ArrayList<PaiementParentItem>

    private var adapterRecyclerView: RecyclerView.Adapter<*>? = null
    private var parentRecyclerView: RecyclerView? = null

    private val paiementViewModel by activityViewModels<PaiementViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentPaiementBinding.inflate(layoutInflater)


        binding.closeButton.setOnClickListener {
            binding.logOut.visibility = View.GONE
            binding.optionsLayout.visibility = View.GONE
        }

//        parentRecyclerView = binding.parentRecyclerView
//
//        parentRecyclerView?.setHasFixedSize(true)
//        parentRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
//
//        parentList = ArrayList()
//
//        showData()
//
//        adapterRecyclerView = PaiementParentRecyclerViewAdapter(parentList)
//        parentRecyclerView?.adapter = adapterRecyclerView



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? BottomNavBarHandler)?.setUpBottomNavBar()
        initRecyclerView()
    }

    fun operatorTelecomChoisie(operator: String){
        paiementViewModel.apply {
            setOperatorTelecom(operator)
        }
    }


    override fun onOptionClicked(option: String) {
        when (option) {
            "IAM Recharges" -> {

                println("Performing action for IAM Recharges")
                println("bangalla")

//                activity?.supportFragmentManager?.beginTransaction()
//                    ?.replace(R.id.fragment_container, IamRechargeFragment())
//                    ?.addToBackStack(null)
//                    ?.commit()
                operatorTelecomChoisie("IAM")
                Navigation.findNavController(binding.root).navigate(R.id.action_paiementFragment_to_iamRechargeFragment)
            }
            "IAM Factures: Mobile" -> {
                operatorTelecomChoisie("IAM")
                println("Performing action for IAM Factures: Mobile")
            }
            "IAM Factures: INTERNET" -> {
                operatorTelecomChoisie("IAM")
                println("Performing action for IAM Factures: INTERNET")
            }
            "Orange Recharges" -> {
                operatorTelecomChoisie("Orange")
                println("Performing action for Orange Recharges")
            }
            "Orange Factures: Mobile" -> {
                operatorTelecomChoisie("Orange")
                println("Performing action for Orange Factures: Mobile")
            }
            "Orange Factures: INTERNET" -> {
                operatorTelecomChoisie("Orange")
                println("Performing action for Orange Factures: INTERNET")
            }
            "Inwi Recharges" -> {
                operatorTelecomChoisie("Inwi")
                println("Performing action for Inwi Recharges")
            }
            "Inwi Factures: Mobile" -> {
                operatorTelecomChoisie("Inwi")
                println("Performing action for Inwi Factures: Mobile")
            }
            "Inwi Factures: INTERNET" -> {
                operatorTelecomChoisie("Inwi")
                println("Performing action for Inwi Factures: INTERNET")
            }
            else -> {
                // Handle other cases
                println("No action defined for $option")
            }
        }
    }


    override fun onChildItemClick(item: PaiementChildItem) {
        // Clear any previous options RecyclerView if exists
        binding.optionsLayout.removeAllViews()
        Log.d("ccTT", "Child clicked")

        // Create a new RecyclerView for options
        val optionsRecyclerView = RecyclerView(requireContext())
        optionsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Populate optionsRecyclerView with options based on the clicked child item
        val optionsAdapter = OptionsAdapter(getOptionsForChildItem(item))
        optionsAdapter.setOnOptionClickListener(this@PaiementFragment)

        val options = getOptionsForChildItem(item)






        optionsRecyclerView.adapter = optionsAdapter

        // Add optionsRecyclerView to the layout
        binding.optionsLayout.addView(optionsRecyclerView)
        binding.logOut.visibility = View.VISIBLE
        binding.optionsLayout.visibility = View.VISIBLE
    }



    private fun getOptionsForChildItem(item: PaiementChildItem): List<String> {
        val options = when (item.title) {
            "IAM" -> listOf("IAM Recharges", "IAM Factures: Mobile", "IAM Factures: INTERNET")
            "Orange" -> listOf("Orange Recharges", "Orange Factures: Mobile", "Orange Factures: INTERNET")
            "Inwi" -> listOf("Inwi Recharges", "Inwi Factures: Mobile", "Inwi Factures: INTERNET")
            else -> emptyList()
        }


        return options
    }


    private fun initRecyclerView() {
        val items: ArrayList<PaiementParentItem> = ArrayList<PaiementParentItem>()
        val childItems1 : ArrayList<PaiementChildItem>  = ArrayList<PaiementChildItem>()
        childItems1.add(PaiementChildItem("IAM", "iam"))
        childItems1.add(PaiementChildItem("Orange", "orange"))
        childItems1.add(PaiementChildItem("Inwi", "inwi"))
//        childItems1.add(PaiementChildItem("Inwi Recharge", "inwi"))
        items.add(PaiementParentItem("TELEPHONIE ET INTERNET", "telephonie", childItems1))

        val childItem2 = ArrayList<PaiementChildItem>()
        childItem2.add(PaiementChildItem("Paiement de vignette", "vignette"))
        items.add(
            PaiementParentItem(
                "ADMINISTRATION",
                "ic_home",
                childItem2
            )
        )
        val childItem3 = ArrayList<PaiementChildItem>()
        childItem3.add(PaiementChildItem("REDAL", "redal"))
        childItem3.add(PaiementChildItem("RAMSA", "ramsa"))
        childItem3.add(PaiementChildItem("Amendis Tanger", "amendis"))
        items.add(
            PaiementParentItem(
                "EAU ET ELECTRICITE",
                "eauelectricity",
                childItem3
            )
        )


        adapterRecyclerView = PaiementParentRecyclerViewAdapter(items,this)
        parentRecyclerView = binding.parentRecyclerView
        parentRecyclerView?.layoutManager =
        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        parentRecyclerView?.adapter = adapterRecyclerView

//        LinearLayoutManager(requireContext())
    }




}


