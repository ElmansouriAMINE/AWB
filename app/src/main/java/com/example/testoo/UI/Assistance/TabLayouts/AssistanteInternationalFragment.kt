package com.example.testoo.UI.Assistance.TabLayouts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testoo.Domain.models.Assistance
import com.example.testoo.R
import com.example.testoo.UI.Adapters.AssistanceListAdapter
import com.example.testoo.databinding.FragmentAssistanteInternationalBinding


class AssistanteInternationalFragment : Fragment() {

    private lateinit var binding:FragmentAssistanteInternationalBinding
    private var adapterAssistance: RecyclerView.Adapter<*>? = null
    private var recyclerViewAssistance: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAssistanteInternationalBinding.inflate(layoutInflater)
        initRecyclerView()




        return binding.root
    }

    private fun initRecyclerView() {
        val items: ArrayList<Assistance> = ArrayList<Assistance>()
        items.add(Assistance("france",  "France","+33 820 89 98 99"))
        items.add(Assistance("italy",  "Italie","+39 0144 230095"))
        items.add(Assistance("belgium",  "Belgique","+32 2 588 4830"))
        items.add(Assistance("germany",  "Allemagne","+49 32 221095029"))
        items.add(Assistance("spain",  "Espagne","+34 911 23 15 41"))
        items.add(Assistance("netherlands",  "Hollande","+31 800 5544002"))
        items.add(Assistance("canada",  "Canada","+1 58 76 03 63 53"))
        items.add(Assistance("uk",  "Royaume-Uni","+44 13 61 31 02 12"))


        adapterAssistance = AssistanceListAdapter(items)
        recyclerViewAssistance = binding.view1
        recyclerViewAssistance?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerViewAssistance?.adapter = adapterAssistance
    }



}
