package com.example.testoo.UI.VirementFragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.testoo.R
import com.example.testoo.Utils.BottomNavBarHandler
import com.example.testoo.ViewModels.VirementViewModel
import com.example.testoo.databinding.FragmentVirement3Binding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class VirementFragment3 : Fragment() {

    private lateinit var binding: FragmentVirement3Binding
    private val virementViewModel by activityViewModels<VirementViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVirement3Binding.inflate(layoutInflater)

        val circularProgressBar = binding.
        circularProgressBar.apply {
            progress = 33f
//            setProgressWithAnimation(25f, 1000)
            progressMax = 100f
            progressBarColor = Color.BLACK
            progressBarColorStart = Color.GRAY
            progressBarColorEnd = Color.YELLOW
            backgroundProgressBarColor = Color.GRAY
            backgroundProgressBarColorStart = Color.WHITE
            backgroundProgressBarColorEnd = Color.YELLOW
            roundBorder = true
        }

        circularProgressBar.setProgressWithAnimation(33f, 3000)

        lifecycleScope.launch {
            var progress = 66f
            while (progress <= 100f) {
                delay(60)
                progress += 1f
                circularProgressBar.setProgressWithAnimation(progress, 100)
            }
        }

        binding.buttonContinue.setOnClickListener {
//            activity?.supportFragmentManager?.beginTransaction()
//                ?.replace(R.id.fragment_container, ValidationFragment())
//                ?.addToBackStack(null)
//                ?.commit()

            Navigation.findNavController(binding.root).navigate(R.id.action_virementFragment3_to_validationFragment)

        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? BottomNavBarHandler)?.setUpBottomNavBar()

        virementViewModel.beneficiaire.observe(viewLifecycleOwner, Observer { data ->
            binding.textBeneficiaire1.setText(data)
            Toast.makeText(requireContext(),data, Toast.LENGTH_LONG).show()
        })

        virementViewModel.montant.observe(viewLifecycleOwner, Observer { data2 ->
            binding.textMontant.setText(data2 +" DH")
            Toast.makeText(requireContext(),data2+"", Toast.LENGTH_LONG).show()
        })
        virementViewModel.motif.observe(viewLifecycleOwner, Observer { data2 ->
            Toast.makeText(requireContext(),data2+"", Toast.LENGTH_LONG).show()
        })

        virementViewModel.data.observe(viewLifecycleOwner, Observer { data ->
            binding.textCompteEmetteur.setText("$data")
            Toast.makeText(requireContext(),data+"", Toast.LENGTH_LONG).show()


        })
    }



}
