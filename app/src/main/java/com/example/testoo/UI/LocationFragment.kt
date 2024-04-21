package com.example.testoo.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testoo.UI.Adapters.TransationListAdapter
import com.example.testoo.ViewModels.UserViewModel
import com.example.testoo.databinding.FragmentLocationBinding

import com.example.testoo.Domain.models.Transaction
import com.example.testoo.Domain.models.User
import com.example.testoo.R
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


    private val viewModel: UserViewModel by viewModels()

    private lateinit var binding: FragmentLocationBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLocationBinding.inflate(layoutInflater)



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
        initRecyclerView()
        val transferView = binding.transferView.setOnClickListener {
            bottomSheetFragment.show(childFragmentManager,"BottomSheetDialog")
        }

        binding.requestView.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, UploadCINInfosFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
    }

    private fun initRecyclerView() {
        val items: ArrayList<Transaction> = ArrayList<Transaction>()
        items.add(Transaction("attijariwafa",  "Ali","Ali","Ali" ,"2000","22-06-2022 16:30"))
        items.add(Transaction("attijariwafa",  "Amine","Amine", "Amine","30","24-06-2022 16:30"))
        items.add(Transaction("attijariwafa", "ziad", "ziad","ziad", "560","25-06-2022 16:30"))

        adapterTransaction = TransationListAdapter(items)
        recyclerViewTransaction = binding.view1
        recyclerViewTransaction?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerViewTransaction?.adapter = adapterTransaction
    }




}
