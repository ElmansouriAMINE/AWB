package com.example.testoo.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testoo.Domain.models.Transaction
import com.example.testoo.R
import com.example.testoo.UI.Adapters.TransationListAdapter
import com.example.testoo.ViewModels.UserViewModel
import com.example.testoo.databinding.FragmentHistoriqueTransactionsBinding
import com.google.firebase.auth.FirebaseAuth


class HistoriqueTransactionsFragment : Fragment() {
    private var adapterTransaction: RecyclerView.Adapter<*>? = null
    private var recyclerViewTransaction: RecyclerView? = null
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val bottomSheetFragment = BottomSheetFragment()

    private lateinit var auth: FirebaseAuth


    private val viewModel: UserViewModel by viewModels()


    private lateinit var binding : FragmentHistoriqueTransactionsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHistoriqueTransactionsBinding.inflate(layoutInflater)
//        binding.searchView.setOnQueryTextFocusChangeListener()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val items: ArrayList<Transaction> = ArrayList<Transaction>()
        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))


        adapterTransaction = TransationListAdapter(items)
        recyclerViewTransaction = binding.view1
        recyclerViewTransaction?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerViewTransaction?.adapter = adapterTransaction
    }


}
