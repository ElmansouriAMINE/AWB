package com.example.testoo.UI.Payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testoo.Domain.models.PaiementChildItem
import com.example.testoo.Domain.models.PaiementParentItem
import com.example.testoo.R
import com.example.testoo.UI.Adapters.PaiementParentRecyclerViewAdapter
import com.example.testoo.databinding.FragmentPaiementBinding


class PaiementFragment : Fragment() {

    private lateinit var binding: FragmentPaiementBinding
    private lateinit var parentRecyclerView: RecyclerView
    private lateinit var parentList : ArrayList<PaiementParentItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentPaiementBinding.inflate(layoutInflater)

        parentRecyclerView = binding.parentRecyclerView

        parentRecyclerView.setHasFixedSize(true)
        parentRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        parentList = ArrayList()

        showData()

        val adapter = PaiementParentRecyclerViewAdapter(parentList)
        parentRecyclerView.adapter = adapter



        return binding.root
    }

    private fun showData() {
        val childItems1 = ArrayList<PaiementChildItem>()
        childItems1.add(PaiementChildItem("Orange Recharge", R.drawable.orange))
        childItems1.add(PaiementChildItem("Orange", R.drawable.orange))
        childItems1.add(PaiementChildItem("Inwi", R.drawable.inwi))
        childItems1.add(PaiementChildItem("Inwi Recharge", R.drawable.inwi))

        parentList.add(PaiementParentItem("TELEPHONIE ET INTERNET", R.drawable.telephonie, childItems1))


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

    }



}
