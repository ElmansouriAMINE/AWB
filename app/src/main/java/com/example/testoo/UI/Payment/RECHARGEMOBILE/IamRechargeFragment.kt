package com.example.testoo.UI.Payment.RECHARGEMOBILE

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.testoo.R
import com.example.testoo.Utils.BottomNavBarHandler
import com.example.testoo.ViewModels.PaiementViewModel
import com.example.testoo.databinding.FragmentIamRechargeBinding


class IamRechargeFragment : Fragment() {

    private lateinit var binding:FragmentIamRechargeBinding

    private val paiementViewModel by activityViewModels<PaiementViewModel>()

    private val maxLength = 9


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentIamRechargeBinding.inflate(layoutInflater)











        binding.numeroEt.filters = arrayOf(InputFilter.LengthFilter(maxLength))


        binding.montantET.setOnClickListener {
            showNumberListDialog()
        }
        binding.button.setOnClickListener {

//            activity?.supportFragmentManager?.beginTransaction()
//                ?.replace(R.id.fragment_container, SelectionRechargeFragment())
//                ?.addToBackStack(null)
//                ?.commit()
            val numero = binding.numeroEt.text.toString()
            val montant = binding.montantET.text.toString()
            if(numero.isNotEmpty() && montant.isNotEmpty()){
                paiementViewModel.apply {
                    setNumero("+212${numero}")
                    setMontant("${montant}")
                }
                Navigation.findNavController(binding.root).navigate(R.id.action_iamRechargeFragment_to_selectionRechargeFragment)
            }
            else if(numero.isEmpty() && montant.isEmpty()) {
                Toast.makeText(requireContext(),"Please fill in the fields",Toast.LENGTH_SHORT).show()
            }
            else if (numero.isEmpty()){
                Toast.makeText(requireContext(),"Phone number is empty, Fill it up",Toast.LENGTH_SHORT).show()
            }
            else if (montant.isEmpty()){
                Toast.makeText(requireContext(),"montant is empty, Fill it up",Toast.LENGTH_SHORT).show()
            }



        }

        return binding.root
    }

    private fun showNumberListDialog() {
        val numbers = (10..50  step 10).map { it.toString() }.toTypedArray()
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Montant")
            .setItems(numbers) { _, which ->
                binding.montantET.setText(numbers[which])
            }
        builder.create().show()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? BottomNavBarHandler)?.setUpBottomNavBar()
        paiementViewModel.operatorTelecom.observe(viewLifecycleOwner, Observer { data ->
            Toast.makeText(requireContext(),data, Toast.LENGTH_LONG).show()
        })
    }


}
