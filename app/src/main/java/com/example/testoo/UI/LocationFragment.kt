package com.example.testoo.UI

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testoo.UI.Adapters.TransationListAdapter
import com.example.testoo.ViewModels.UserViewModel
import com.example.testoo.databinding.FragmentLocationBinding

import com.example.testoo.Domain.models.Transaction
import com.example.testoo.Domain.models.User
import com.example.testoo.R
import com.example.testoo.UI.Payment.PaiementFragment
import com.example.testoo.Utils.BottomNavBarHandler
import com.example.testoo.ViewModels.TransactionViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
//import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LocationFragment : Fragment() {
    private var adapterTransaction: RecyclerView.Adapter<*>? = null
    private var recyclerViewTransaction: RecyclerView? = null
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val bottomSheetFragment = BottomSheetFragment()
    private val userViewModel by activityViewModels<UserViewModel>()
    private val transationViewModel by activityViewModels<TransactionViewModel>()

    private lateinit var auth: FirebaseAuth


    private val viewModel: UserViewModel by viewModels()

    private lateinit var binding: FragmentLocationBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()

        binding = FragmentLocationBinding.inflate(layoutInflater)

        binding.logOut.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Log Out")
            builder.setMessage("Are you sure you want to log out?")
            builder.setPositiveButton("Yes") { dialog, which ->
                auth.signOut()
//                val signInFragment = SignInFragment()
//                parentFragmentManager.beginTransaction()
//                    .replace(R.id.fragment_container, signInFragment)
//                    .commit()
                Navigation.findNavController(binding.root).navigate(R.id.toSignInFragment)
            }
            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }




        currentUser?.let { user ->
            val current_user=FirebaseDatabase.getInstance().reference
                .child("users")
                .child(currentUser.uid)

            val currentUserCompte= FirebaseDatabase.getInstance().reference
                .child("comptes")

            // the coroutineScope that I use for calling the compte of the current User
            viewLifecycleOwner.lifecycleScope.launch {
                    val compte = withContext(Dispatchers.IO) {
                        viewModel.getCompteForUserId(userId = user.uid)
                    }

                println("this is the compte: $compte")
                binding.compteSolde.setText(compte?.solde.toString())

                }



            current_user.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val user = snapshot.getValue(User::class.java)
                        val userName = user?.userName
                        binding.currentUserName.setText(if (user?.userName.isNullOrEmpty()) "Nothing" else userName)


                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Gérer l'erreur ici
                }
            })
        }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? BottomNavBarHandler)?.setUpBottomNavBar()
//        currentUser?.let {
//            viewLifecycleOwner.lifecycleScope.launch {
//                val userCrr = withContext(Dispatchers.IO) {
//                    userViewModel.getCurrentUser(currentUser.uid)
//                }
//                userCrr?.let { it1 -> initRecyclerView(it1) }
//            }
//        }

        binding.transferView.setOnClickListener {
            bottomSheetFragment.show(childFragmentManager,"BottomSheetDialog")
        }

        binding.history.setOnClickListener {
           Navigation.findNavController(binding.root).navigate(R.id.toHistoriqueTransactionsFragment)
        }
        binding.seeAll.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.toHistoriqueTransactionsFragment)
        }
        binding.iconSeeAll.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.toHistoriqueTransactionsFragment)
        }


        binding.paiement.setOnClickListener {

            Navigation.findNavController(binding.root).navigate(R.id.toPaiementFragment)
        }
        binding.bankingCardsManagement.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.toBankingCardsFragment)
        }
        binding.requestView.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.toExampleChartFragment)
        }

        binding.accountsManagements.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.action_locationFragment_to_bankingAccountsFragment)
        }

        binding.parametreDarkLightMode.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.toDarkLightModeFragment)
        }



        currentUser?.let {
            viewLifecycleOwner.lifecycleScope.launch{

                val userCrr = withContext(Dispatchers.IO){
                    userViewModel.getCurrentUser(currentUser.uid)

                }
                val transactions = withContext(Dispatchers.IO){
                    transationViewModel.getRecentThreeTransactions(currentUser.uid,userCrr!!)
                }

                initTransactionsForCurrentUser(
                    transactions as ArrayList<Transaction>,currentUser.uid,
                    userCrr!!
                )



            }
        }




}


    private fun initTransactionsForCurrentUser(items : ArrayList<Transaction>,userId: String,user: User){
        adapterTransaction = TransationListAdapter(items,userId,user)
        recyclerViewTransaction = binding.view1
        recyclerViewTransaction?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerViewTransaction?.adapter = adapterTransaction

    }

//    private fun initRecyclerView(user: User) {
//        val items: ArrayList<Transaction> = ArrayList<Transaction>()
//        items.add(Transaction("orange",  "Ali","","","Ali","Ali" ,"2000","22-06-2022 16:30"))
//        items.add(Transaction("attijariwafa",  "Amine","","","Amine", "Amine","30","24-06-2022 16:30"))
//        items.add(Transaction("attijariwafa", "ziad", "","","ziad","ziad", "560","25-06-2022 16:30"))
//
//        adapterTransaction = TransationListAdapter(items,"${currentUser?.uid}",user)
//        recyclerViewTransaction = binding.view1
//        recyclerViewTransaction?.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        recyclerViewTransaction?.adapter = adapterTransaction
//    }




}

