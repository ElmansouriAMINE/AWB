package com.example.testoo.UI.Payment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testoo.Domain.models.PaiementChildItem
import com.example.testoo.Domain.models.PaiementParentItem
import com.example.testoo.Domain.models.Transaction
import com.example.testoo.R
import com.example.testoo.UI.Adapters.OptionsAdapter
import com.example.testoo.UI.Adapters.PaiementChildRecyclerViewAdapter
import com.example.testoo.UI.Adapters.PaiementParentRecyclerViewAdapter
import com.example.testoo.UI.Adapters.TransationListAdapter
import com.example.testoo.databinding.FragmentPaiementBinding


class PaiementFragment : Fragment(), PaiementChildRecyclerViewAdapter.OnChildItemClickListener {

    private lateinit var binding: FragmentPaiementBinding
//    private lateinit var parentRecyclerView: RecyclerView
//    private lateinit var parentList : ArrayList<PaiementParentItem>

    private var adapterRecyclerView: RecyclerView.Adapter<*>? = null
    private var parentRecyclerView: RecyclerView? = null

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
        initRecyclerView()
    }
//    override fun onChildItemClick(item: PaiementChildItem) {
//        // Clear any previous options RecyclerView if exists
////        binding.optionsLayout.removeAllViews()
////        print("crcr${getOptionsForChildItem(item)}")
////        print("lol")
////
////        // Create a new RecyclerView for options
////        val optionsRecyclerView = RecyclerView(requireContext())
////        optionsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
////
////        // Populate optionsRecyclerView with options based on the clicked child item
////        val optionsAdapter = OptionsAdapter(getOptionsForChildItem(item))
////        print("crcr${getOptionsForChildItem(item)}")
////        optionsRecyclerView.adapter = optionsAdapter
////
////        // Add optionsRecyclerView to the layout
////        binding.optionsLayout.addView(optionsRecyclerView)
//    }

    override fun onChildItemClick(item: PaiementChildItem) {
        // Clear any previous options RecyclerView if exists
        binding.optionsLayout.removeAllViews()
        Log.d("ccTT", "Child clicked")

        // Create a new RecyclerView for options
        val optionsRecyclerView = RecyclerView(requireContext())
        optionsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Populate optionsRecyclerView with options based on the clicked child item
        val optionsAdapter = OptionsAdapter(getOptionsForChildItem(item))
        optionsRecyclerView.adapter = optionsAdapter

        // Add optionsRecyclerView to the layout
        binding.optionsLayout.addView(optionsRecyclerView)
        binding.logOut.visibility = View.VISIBLE
        binding.optionsLayout.visibility = View.VISIBLE
    }


    private fun getOptionsForChildItem(item: PaiementChildItem): List<String> {

        return when (item.title) {
            "Orange Recharge" -> listOf("Option 1", "Option 2", "Option 3")
            "Orange" -> listOf("Option A", "Option B", "Option C")
            // Add more cases for other child items if needed
            else -> emptyList() // Return an empty list if no options are available
        }
    }

    private fun initRecyclerView() {
        val items: ArrayList<PaiementParentItem> = ArrayList<PaiementParentItem>()
        val childItems1 : ArrayList<PaiementChildItem>  = ArrayList<PaiementChildItem>()
        childItems1.add(PaiementChildItem("Orange Recharge", "orange"))
        childItems1.add(PaiementChildItem("Orange", "orange"))
        childItems1.add(PaiementChildItem("Inwi", "inwi"))
        childItems1.add(PaiementChildItem("Inwi Recharge", "inwi"))
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


//    private fun showData() {
//        val childItems1 : ArrayList<PaiementChildItem>  = ArrayList<PaiementChildItem>()
//        childItems1.add(PaiementChildItem("Orange Recharge", "orange"))
//        childItems1.add(PaiementChildItem("Orange", "orange"))
//        childItems1.add(PaiementChildItem("Inwi", "inwi"))
//        childItems1.add(PaiementChildItem("Inwi Recharge", "inwi"))
//
//        parentList.add(PaiementParentItem("TELEPHONIE ET INTERNET", "inwi", childItems1))


//        val childItem2 = ArrayList<PaiementChildItem>()
//        childItem2.add(PaiementChildItem("Paiement de vignette", "vignette"))
//        parentList.add(
//            PaiementParentItem(
//                "ADMINISTRATION",
//                "ic_home",
//                childItem2
//            )
//        )
//        val childItem3 = ArrayList<PaiementChildItem>()
//        childItem3.add(PaiementChildItem("REDAL", "redal"))
//        childItem3.add(PaiementChildItem("RAMSA", "ramsa"))
//        childItem3.add(PaiementChildItem("Amendis Tanger", "amendis"))
//        parentList.add(
//            PaiementParentItem(
//                "EAU ET ELECTRICITE",
//                "eauelectricity",
//                childItem3
//            )
//        )

//    }



}


