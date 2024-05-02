package com.example.testoo.UI.VirementFragments

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.testoo.R
import com.example.testoo.ViewModels.VirementViewModel
import com.example.testoo.databinding.FragmentVirement2Binding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class VirementFragment2 : Fragment() {

    private lateinit var binding:FragmentVirement2Binding

    private val virementViewModel by activityViewModels<VirementViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        binding = FragmentVirement2Binding.inflate(layoutInflater)

        binding = FragmentVirement2Binding.inflate(layoutInflater)

//        val circularProgressBar = binding.
//        circularProgressBar.apply {
//            progress = 50f
//            setProgressWithAnimation(50f, 3000)
//            progressMax = 100f
//            progressBarColor = Color.BLACK
//            progressBarColorStart = Color.GRAY
//            progressBarColorEnd = Color.RED
//            backgroundProgressBarColor = Color.GRAY
//            backgroundProgressBarColorStart = Color.WHITE
//            backgroundProgressBarColorEnd = Color.RED
//            roundBorder = true
//        }

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
            var progress = 33f
            while (progress <= 66f) {
                delay(60)
                progress += 1f
                circularProgressBar.setProgressWithAnimation(progress, 100)
            }
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.autoCompleteMontant.setText("$progress")
                // Update the progress variable to the value of autoCompleteMontant
                val newProgress = binding.autoCompleteMontant.text.toString().toIntOrNull() ?: 0
                updateVirementData(newProgress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        // Assuming binding.autoCompleteMontant is an AutoCompleteTextView
        binding.autoCompleteMontant.inputType = InputType.TYPE_CLASS_NUMBER
        binding.autoCompleteMontant.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                s?.toString()?.toIntOrNull()?.let { value ->
                    if (value in 10..5000) { // Assuming the progress bar range is from 0 to 100
                        binding.seekBar.progress = value
                    }
                }
            }
        })




        binding.buttonContinue.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, VirementFragment3())
                ?.addToBackStack(null)
                ?.commit()
            binding.autoCompleteMontant.text.toString().toIntOrNull()
                ?.let { it1 -> updateVirementData(it1) }
        }









        return binding.root
    }

    private fun updateVirementData(newProgress: Int) {

        val motif = "${binding.autoCompleteMotif.text}"
        val montant ="${newProgress}"
        val data2 = "${binding.autoCompleteMotif.text} - ${newProgress} - ${binding.autoCompleteMotif.text}"
        virementViewModel.apply {
            setData2(data2)
            setMotif(motif)
            setMontant(montant)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        virementViewModel.data.observe(viewLifecycleOwner, Observer { data ->
//            Toast.makeText(requireContext(),data,Toast.LENGTH_LONG).show()
//        })
        virementViewModel.beneficiaire.observe(viewLifecycleOwner, Observer { data ->
            Toast.makeText(requireContext(),data,Toast.LENGTH_LONG).show()
        })
        virementViewModel.beneficiaireId.observe(viewLifecycleOwner, Observer { data2 ->
            Toast.makeText(requireContext(),data2,Toast.LENGTH_LONG).show()
        })
//
//        virementViewModel.data2.observe(viewLifecycleOwner, Observer { data2 ->
//            Toast.makeText(requireContext(),data2,Toast.LENGTH_LONG).show()
//        })
    }


}
