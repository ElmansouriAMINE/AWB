package com.example.testoo.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testoo.Domain.models.Transaction
import com.example.testoo.Domain.models.User
import com.example.testoo.R
import com.example.testoo.UI.Adapters.TransationListAdapter
import com.example.testoo.ViewModels.TransactionViewModel
import com.example.testoo.ViewModels.UserViewModel
import com.example.testoo.databinding.FragmentHistoriqueTransactionsBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import java.util.Locale.filter
import kotlin.collections.ArrayList


class HistoriqueTransactionsFragment : Fragment() {
    private var adapterTransaction: RecyclerView.Adapter<*>? = null
    private var recyclerViewTransaction: RecyclerView? = null
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val bottomSheetFragment = BottomSheetFragment()
    private lateinit  var originalTransactions: ArrayList<Transaction>
    private lateinit var filteredTransactions: ArrayList<Transaction>

    private val userViewModel by activityViewModels<UserViewModel>()

    private val transationViewModel by activityViewModels<TransactionViewModel>()

    private lateinit var auth: FirebaseAuth


    private val viewModel: UserViewModel by viewModels()


    private lateinit var binding : FragmentHistoriqueTransactionsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHistoriqueTransactionsBinding.inflate(layoutInflater)

        return binding.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        initRecyclerView()

        currentUser?.let {
            viewLifecycleOwner.lifecycleScope.launch{

                val userCrr = withContext(Dispatchers.IO){
                  userViewModel.getCurrentUser(currentUser.uid)

                }
                val transactions = withContext(Dispatchers.IO){
                    transationViewModel.getAllTransactions(currentUser.uid,userCrr!!)
                }

                originalTransactions = transactions
                filteredTransactions = originalTransactions

                initTransactionsForCurrentUser(
                    filteredTransactions as ArrayList<Transaction>,currentUser.uid,
                    userCrr!!
                )

                binding.searchView.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(msg: String): Boolean {
                        // inside on query text change method we are
                        // calling a method to filter our recycler view.
//                        filter(msg)
                        return false
                    }
                })



//                userCrr?.userName?.let { it1 ->
//                    initTransactionsForCurrentUser(transactions,currentUser.uid,
//                        it1
//                    )
//                }

            }
        }
    }


    private fun filter(query: String) {
        filteredTransactions = if (query.isEmpty()) {
            originalTransactions
        } else {
            originalTransactions.filter { transaction ->
                transaction.receiverName?.contains(query, ignoreCase = true) == true
            } as ArrayList<Transaction>
        }

        adapterTransaction?.let {
            (it as TransationListAdapter).updateList(filteredTransactions)
        }
    }


//    private fun initRecyclerView() {
//        val items: ArrayList<Transaction> = ArrayList<Transaction>()
//        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
//        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
//        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
//        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
//        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
//        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
//        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
//        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
//        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
//        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
//        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
//        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
//        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
//        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
//        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
//        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
//        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
//        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
//        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
//        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
//        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
//        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
//        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
//        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
//        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
//        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
//        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
//        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
//        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
//        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
//        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
//        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
//        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
//        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
//        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
//        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
//        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
//        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
//        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
//        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
//        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
//        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
//        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
//        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
//        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
//        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
//        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
//        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
//        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
//        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
//        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
//        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
//        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
//        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
//        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
//        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
//        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
//
//
//        adapterTransaction = TransationListAdapter(items)
//        recyclerViewTransaction = binding.view1
//        recyclerViewTransaction?.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        recyclerViewTransaction?.adapter = adapterTransaction
//    }

    private fun initTransactionsForCurrentUser(items : ArrayList<Transaction>,userId: String,user: User){
        adapterTransaction = TransationListAdapter(items,userId,user)
        recyclerViewTransaction = binding.view1
        recyclerViewTransaction?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerViewTransaction?.adapter = adapterTransaction

    }


}
