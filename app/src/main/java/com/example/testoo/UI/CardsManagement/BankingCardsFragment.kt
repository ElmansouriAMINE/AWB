package com.example.testoo.UI.CardsManagement

import ImageAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.testoo.Domain.models.Carte
import com.example.testoo.Domain.models.ImageItem
import com.example.testoo.Domain.models.Transaction
import com.example.testoo.Domain.models.User
import com.example.testoo.R
import com.example.testoo.UI.Adapters.TransationListAdapter
import com.example.testoo.UI.BottomSheetFragment
import com.example.testoo.ViewModels.TransactionViewModel
import com.example.testoo.ViewModels.UserViewModel
import com.example.testoo.databinding.FragmentBankingCardsBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList


class BankingCardsFragment : Fragment() {

    private var adapterTransaction: RecyclerView.Adapter<*>? = null
    private var recyclerViewTransaction: RecyclerView? = null
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val bottomSheetFragment = BottomSheetFragment()
    private lateinit  var originalTransactions: ArrayList<Transaction>
    private lateinit var filteredTransactions: ArrayList<Transaction>
    private var cardsListe: ArrayList<Carte> = ArrayList()

    private val userViewModel by activityViewModels<UserViewModel>()

    private val transationViewModel by activityViewModels<TransactionViewModel>()

    private lateinit var auth: FirebaseAuth


    private val viewModel: UserViewModel by viewModels()


    private lateinit var binding: FragmentBankingCardsBinding

    private lateinit var  pageChangeListener: ViewPager2.OnPageChangeCallback

    private val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8,0,8,0)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentBankingCardsBinding.inflate(layoutInflater)


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.viewPager2.unregisterOnPageChangeCallback(pageChangeListener)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentUser?.let {
            viewLifecycleOwner.lifecycleScope.launch {

                val userCrr = withContext(Dispatchers.IO) {
                    userViewModel.getCurrentUser(currentUser.uid)

                }
                val transactions = withContext(Dispatchers.IO) {
                    transationViewModel.getAllCardTransactions(currentUser.uid)
                }

                originalTransactions = transactions
                filteredTransactions = originalTransactions

                initTransactionsForCurrentUser(
                    filteredTransactions as ArrayList<Transaction>, currentUser.uid,
                    userCrr!!
                )

                val cardsInfos = withContext(Dispatchers.IO) {
                    userViewModel.getCardsForCurrentUser(currentUser.uid)
                }

                println("cardsInfos: $cardsInfos")

                val colors = listOf("bluecard", "goldcard", "whitecard")

                val imageList1 = cardsInfos.mapIndexed { index, card ->
                    val color = colors[index % colors.size]
                    userCrr?.userName?.let { userName ->
                        card.dateExpiration?.let { dateExpiration ->
                            card.numeroCarte?.let { numeroCarte ->
                                ImageItem(
                                    UUID.randomUUID().toString(),
                                    color,
                                    dateExpiration,
                                    numeroCarte,
                                    userName
                                )
                            }
                        }
                    }
                }.filterNotNull()



                val imageList = ArrayList(imageList1)

                println("imageList: $imageList")

                val imageAdapter = ImageAdapter()
                binding.viewPager2.adapter = imageAdapter
                imageAdapter.submitList(imageList)

                print("hhhhhh ${imageList.size}")

                if (imageList.isNotEmpty()) {
                    val dotsImage = Array(imageList.size) { ImageView(requireContext()) }

                    dotsImage.forEach {
                        it.setImageResource(R.drawable.non_active_dot)
                        binding.slideDots.addView(it, params)
                    }

                    dotsImage[0].setImageResource(R.drawable.active_dot)

                    pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                            dotsImage.mapIndexed { index, imageView ->
                                if (position == index) {
                                    imageView.setImageResource(R.drawable.active_dot)
                                } else {
                                    imageView.setImageResource(R.drawable.non_active_dot)
                                }
                            }
                            super.onPageSelected(position)
                        }
                    }

                    binding.viewPager2.registerOnPageChangeCallback(pageChangeListener)
                } else {
                    println("Error: No images available to display")
                }


                binding.gestionCard.setOnClickListener {
                    Navigation.findNavController(binding.root).navigate(R.id.action_bankingCardsFragment_to_cardConfigFragment)
                }

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



}