package com.example.testoo.UI.NewEntreeRelation

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.testoo.R
import com.example.testoo.Utils.BottomNavBarHandler
import com.example.testoo.ViewModels.EntreeRelationViewModel
import com.example.testoo.databinding.FragmentRenseignementBancaire1Binding
import java.util.*
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope

class RenseignementBancaireFragment1 : Fragment() {


    private lateinit var binding: FragmentRenseignementBancaire1Binding
    private val entreeRelationViewModel by activityViewModels<EntreeRelationViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRenseignementBancaire1Binding.inflate(layoutInflater)


        binding.dateEmbaucheEt.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.dateEmbaucheEt.setText(selectedDate)
            }, year, month, day)

            datePickerDialog.show()
        }

        binding.button.setOnClickListener{
            val employerName = binding.nomEmployeurEt.text.toString()
            val hiringdate=binding.dateEmbaucheEt.text.toString()
            val previousEmployer = binding.previousEmployerEt.text.toString()
            val annualsalary=binding.salaireAnnuelET.text.toString()
            if(employerName.isNotEmpty() && hiringdate.isNotEmpty() && previousEmployer.isNotEmpty() && annualsalary.isNotEmpty()){
                Navigation.findNavController(binding.root).navigate(R.id.action_renseignementBancaireFragment1_to_renseignementBancaireFragment2)
            }
            else{
                Toast.makeText(requireContext(),"Please fill in all the fields",Toast.LENGTH_SHORT).show()
            }

        }








        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? BottomNavBarHandler)?.setUpBottomNavBar()

//        entreeRelationViewModel.userName.observe(viewLifecycleOwner, Observer { data ->
//            Toast.makeText(requireContext(),data,Toast.LENGTH_LONG).show()
//        })
//        entreeRelationViewModel.password.observe(viewLifecycleOwner, Observer { data2 ->
//            Toast.makeText(requireContext(),data2,Toast.LENGTH_LONG).show()
//        })
//        entreeRelationViewModel.email.observe(viewLifecycleOwner, Observer { data2 ->
//            Toast.makeText(requireContext(),data2,Toast.LENGTH_LONG).show()
//        })
//        entreeRelationViewModel.phoneNumber.observe(viewLifecycleOwner, Observer { data2 ->
//            Toast.makeText(requireContext(),data2,Toast.LENGTH_LONG).show()
//        })


    }

}
