package com.example.testoo.UI.Payment.IAM

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.testoo.R
import com.example.testoo.databinding.FragmentIamRechargeBinding


class IamRechargeFragment : Fragment() {

    private lateinit var binding:FragmentIamRechargeBinding

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

            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, SelectionRechargeFragment())
                ?.addToBackStack(null)
                ?.commit()

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
    }


}
