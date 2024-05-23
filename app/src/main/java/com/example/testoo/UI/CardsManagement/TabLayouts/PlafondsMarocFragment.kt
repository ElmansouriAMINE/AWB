package com.example.testoo.UI.CardsManagement.TabLayouts

import android.os.Bundle
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
import com.example.testoo.ViewModels.CardsConfigViewModel
import com.example.testoo.databinding.FragmentPlafondsMarocBinding
import kotlinx.coroutines.launch


//class PlafondsMarocFragment : Fragment() {
//
//    private lateinit var binding :  FragmentPlafondsMarocBinding
//
//    private val cardsConfigViewModel by activityViewModels<CardsConfigViewModel>()
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        binding= FragmentPlafondsMarocBinding.inflate(layoutInflater)
//
//        val currentCardItem = cardsConfigViewModel.currentCardItem.value
//
//        currentCardItem?.let { card->
////            binding.seekBarMontantRetraitMaroc.progress = card.plafond?.retraitMaroc?.toIntOrNull()!!
////            binding.seekBarMontantInternetMaroc.progress = card.plafond?.internetMaroc?.toIntOrNull()!!
////            binding.seekBarMontantTPEMaroc.progress = card.plafond?.tpeMaroc?.toIntOrNull()!!
////            binding.montantRetraitMaroc.setText("${card.plafond?.retraitMaroc?.toIntOrNull()!!}")
////            binding.montantInternetMaroc.setText("${card.plafond?.internetMaroc?.toIntOrNull()!!}")
////            binding.montantTPEMaroc.setText("${card.plafond?.tpeMaroc?.toIntOrNull()!!}")
//
////
//
//            lifecycleScope.launch{
//
//                val currentCard= cardsConfigViewModel.getCurrentCard("${card.numeroCarte}")
//                binding.seekBarMontantRetraitMaroc.progress = currentCard?.plafond?.retraitMaroc!!
//
//                binding.seekBarMontantInternetMaroc.progress= currentCard?.plafond?.internetMaroc!!
//                binding.seekBarMontantTPEMaroc.progress= currentCard?.plafond?.tpeMaroc!!
//
//                binding.montantRetraitMaroc.setText("${currentCard?.plafond?.retraitMaroc!!}")
//                binding.montantInternetMaroc.setText("${currentCard?.plafond?.internetMaroc!!}")
//                binding.montantTPEMaroc.setText("${currentCard?.plafond?.tpeMaroc!!}")
//
//
//
////                ajoute
//
//
//                var isSeekBarChanging = false
//
//                binding.seekBarMontantRetraitMaroc.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                        if (fromUser) {
//                            binding.montantRetraitMaroc.setText("$progress")
//                            cardsConfigViewModel.setRetraitMaroc(progress)
//
//                        }
//                    }
//
//                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
//                        isSeekBarChanging = true
//                    }
//
//                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
//                        isSeekBarChanging = false
//                        cardsConfigViewModel.setRetraitMaroc(binding.seekBarMontantRetraitMaroc.progress)
//                    }
//                })
//
//                binding.seekBarMontantInternetMaroc.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                        if (fromUser) {
//                            binding.montantInternetMaroc.setText("$progress")
//                            cardsConfigViewModel.setInternetMaroc(progress)
//                        }
//                    }
//
//                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
//                        isSeekBarChanging = true
//                    }
//
//                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
//                        isSeekBarChanging = false
//                    }
//                })
//
//
//
//                binding.seekBarMontantTPEMaroc.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                        if (fromUser) {
//                            binding.montantTPEMaroc.setText("$progress")
//                            cardsConfigViewModel.setTpeMaroc(progress)
//                        }
//                    }
//
//                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
//                        isSeekBarChanging = true
//                    }
//
//                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
//                        isSeekBarChanging = false
//                    }
//                })
//
//
//            }
//
//        }
//
//
////        var isSeekBarChanging = false
////
////        binding.seekBarMontantRetraitMaroc.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
////            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
////                if (fromUser) {
////                    binding.montantRetraitMaroc.setText("$progress")
////                    cardsConfigViewModel.setRetraitMaroc(progress)
////
////                }
////            }
////
////            override fun onStartTrackingTouch(seekBar: SeekBar?) {
////                isSeekBarChanging = true
////            }
////
////            override fun onStopTrackingTouch(seekBar: SeekBar?) {
////                isSeekBarChanging = false
////                cardsConfigViewModel.setRetraitMaroc(binding.seekBarMontantRetraitMaroc.progress)
////            }
////        })
////
////        binding.seekBarMontantInternetMaroc.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
////            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
////                if (fromUser) {
////                    binding.montantInternetMaroc.setText("$progress")
////                    cardsConfigViewModel.setInternetMaroc(progress)
////                }
////            }
////
////            override fun onStartTrackingTouch(seekBar: SeekBar?) {
////                isSeekBarChanging = true
////            }
////
////            override fun onStopTrackingTouch(seekBar: SeekBar?) {
////                isSeekBarChanging = false
////            }
////        })
////
////
////
////        binding.seekBarMontantTPEMaroc.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
////            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
////                if (fromUser) {
////                    binding.montantTPEMaroc.setText("$progress")
////                    cardsConfigViewModel.setTpeMaroc(progress)
////                }
////            }
////
////            override fun onStartTrackingTouch(seekBar: SeekBar?) {
////                isSeekBarChanging = true
////            }
////
////            override fun onStopTrackingTouch(seekBar: SeekBar?) {
////                isSeekBarChanging = false
////            }
////        })
//
////        print("GGGG ${binding.montantRetraitMaroc.text.toString()}")
//
////        binding.montantRetraitMaroc.text.toString()?.let{
//////            cardsConfigViewModel.setRetraitMaroc(it.toInt())
////            print("GGGG ${it}")
////        }
//
//
//
//
//
//
//        return binding.root
//    }
//
//
//}


class PlafondsMarocFragment : Fragment() {

    private lateinit var binding: FragmentPlafondsMarocBinding
    private val cardsConfigViewModel by activityViewModels<CardsConfigViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlafondsMarocBinding.inflate(inflater, container, false)
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

                    setupSeekBarListeners()
                }
            }
        }
        return binding.root
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
