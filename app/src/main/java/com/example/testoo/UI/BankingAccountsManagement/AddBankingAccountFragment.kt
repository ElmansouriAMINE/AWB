package com.example.testoo.UI.BankingAccountsManagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.testoo.R
import com.example.testoo.ViewModels.UserViewModel
import com.example.testoo.databinding.FragmentAddBankingAccountBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.runBlocking


class AddBankingAccountFragment : Fragment() {


    private lateinit var binding: FragmentAddBankingAccountBinding

    private val currentUser = FirebaseAuth.getInstance().currentUser

    private val userViewModel by activityViewModels<UserViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBankingAccountBinding.inflate(layoutInflater)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentUser?.let {

            binding.button.setOnClickListener {

                val numeroCompte = binding.numeroCompteEt.text.toString()

                if (isValidAccountNumber(numeroCompte)) {
                    runBlocking {
                        userViewModel.createBankingAccount(currentUser.uid, numeroCompte)
                        Toast.makeText(requireContext(), "Banking account created", Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(binding.root).navigate(R.id.action_addBankingAccountFragment_to_bankingAccountsFragment)
                    }
                } else {
                    Toast.makeText(requireContext(), "Please enter a valid account number", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isValidAccountNumber(accountNumber: String): Boolean {
        val regex = Regex("^(?=(?:\\d*[A-Za-z]\\d*){1}$)[\\dA-Za-z]{15}$")
        return accountNumber.length == 15 && regex.matches(accountNumber)
    }




}
