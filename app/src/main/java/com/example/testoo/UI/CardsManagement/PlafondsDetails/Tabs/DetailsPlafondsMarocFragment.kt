package com.example.testoo.UI.CardsManagement.PlafondsDetails.Tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.testoo.R
import com.example.testoo.ViewModels.CardsConfigViewModel
import com.example.testoo.databinding.FragmentDetailsPlafondsMarocBinding
import com.example.testoo.databinding.FragmentPlafondsMarocBinding
import kotlinx.coroutines.launch



class DetailsPlafondsMarocFragment : Fragment() {

    private lateinit var binding: FragmentDetailsPlafondsMarocBinding
    private val cardsConfigViewModel by activityViewModels<CardsConfigViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsPlafondsMarocBinding.inflate(inflater, container, false)
        val currentCardItem = cardsConfigViewModel.currentCardItem.value

        currentCardItem?.let { card ->
            lifecycleScope.launch {
                val currentCard = cardsConfigViewModel.getCurrentCard(card.numeroCarte)
                currentCard?.plafond?.let { plafond ->
                    binding.seekBarMontantRetraitMaroc.progress = plafond.retraitMaroc!!
                    binding.seekBarMontantInternetMaroc.progress = plafond.internetMaroc!!
                    binding.seekBarMontantTPEMaroc.progress = plafond.tpeMaroc!!

                    binding.montantRetraitMaroc.setText(plafond.retraitMaroc.toString())
                    binding.montantInternetMaroc.setText(plafond.internetMaroc.toString())
                    binding.montantTPEMaroc.setText(plafond.tpeMaroc.toString())

                    disableSeekBars()

                    setupSeekBarListeners()
                }
            }
        }
        return binding.root
    }

    private fun disableSeekBars() {
        binding.seekBarMontantRetraitMaroc.isEnabled = false
        binding.seekBarMontantInternetMaroc.isEnabled = false
        binding.seekBarMontantTPEMaroc.isEnabled = false


    }

    private fun setupSeekBarListeners() {
        binding.seekBarMontantRetraitMaroc.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    binding.montantRetraitMaroc.setText("$progress")
                    cardsConfigViewModel.setRetraitMaroc(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.seekBarMontantInternetMaroc.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    binding.montantInternetMaroc.setText("$progress")
                    cardsConfigViewModel.setInternetMaroc(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.seekBarMontantTPEMaroc.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    binding.montantTPEMaroc.setText("$progress")
                    cardsConfigViewModel.setTpeMaroc(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
}
