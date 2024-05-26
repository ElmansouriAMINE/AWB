package com.example.testoo.UI.Payment.RECHARGEMOBILE

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
import com.example.testoo.ViewModels.PaiementViewModel
import com.example.testoo.databinding.FragmentRecapilatifPaiementBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class RecapilatifPaiementFragment : Fragment() {

    private lateinit var binding: FragmentRecapilatifPaiementBinding
    private val paiementViewModel by activityViewModels<PaiementViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecapilatifPaiementBinding.inflate(layoutInflater)

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

            Navigation.findNavController(binding.root).navigate(R.id.action_recapilatifPaiementFragment_to_otpValidationFragment)

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        paiementViewModel.numero.observe(viewLifecycleOwner, Observer { data ->

            Toast.makeText(requireContext(),data, Toast.LENGTH_LONG).show()
        })

        paiementViewModel.montant.observe(viewLifecycleOwner, Observer { data2 ->
            binding.textMontantRechargeRecap.setText(data2 +" DH")
            Toast.makeText(requireContext(),data2+"", Toast.LENGTH_LONG).show()
        })
        paiementViewModel.operatorTelecom.observe(viewLifecycleOwner, Observer { data2 ->
            Toast.makeText(requireContext(),data2+"", Toast.LENGTH_LONG).show()
        })

        paiementViewModel.compteBancaire.observe(viewLifecycleOwner, Observer { data ->
            binding.textCompteEmetteur.setText("${data.numero.toString()}")
            Toast.makeText(requireContext(),data.numero.toString()+"", Toast.LENGTH_LONG).show()
        })

        paiementViewModel.recharge.observe(viewLifecycleOwner, Observer { data ->
            binding.textReferenceRecharge.setText("REF : ${data.ref}")
            binding.textRechargeType.setText("${data.rechargeType}")
            Toast.makeText(requireContext(),"${data.ref}", Toast.LENGTH_LONG).show()
        })
    }


}
