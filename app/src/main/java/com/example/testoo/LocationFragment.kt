package com.example.testoo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testoo.Adapters.TransationListAdapter
import com.example.testoo.databinding.FragmentLocationBinding
import com.example.testoo.models.Transaction


class LocationFragment : Fragment() {
    private var adapterTransaction: RecyclerView.Adapter<*>? = null
    private var recyclerViewTransaction: RecyclerView? = null
    private val bottomSheetFragment = BottomSheetFragment()

    private lateinit var binding: FragmentLocationBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLocationBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        val transferView = binding.transferView.setOnClickListener {
            bottomSheetFragment.show(childFragmentManager,"BottomSheetDialog")
        }
    }

    private fun initRecyclerView() {
        val items: ArrayList<Transaction> = ArrayList<Transaction>()
        items.add(Transaction("attijariwafa", "Ali", "2000", "22-06-2022 16:30"))
        items.add(Transaction("attijariwafa", "Amine", "30", "24-06-2022 16:30"))
        items.add(Transaction("attijariwafa", "ziad", "560", "25-06-2022 16:30"))

        adapterTransaction = TransationListAdapter(items)
        recyclerViewTransaction = binding.view1
        recyclerViewTransaction?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerViewTransaction?.adapter = adapterTransaction
    }




}

