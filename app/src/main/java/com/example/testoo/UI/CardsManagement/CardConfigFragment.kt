package com.example.testoo.UI.CardsManagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.testoo.R
import com.example.testoo.ViewModels.CardsConfigViewModel
import com.example.testoo.databinding.FragmentCardConfigBinding
import kotlinx.coroutines.launch


class CardConfigFragment : Fragment() {

    private lateinit var binding: FragmentCardConfigBinding
    private val cardsConfigViewModel by activityViewModels<CardsConfigViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardConfigBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentCardItem = cardsConfigViewModel.currentCardItem.value

        currentCardItem?.let {
            val imageResourceId =
                resources.getIdentifier(it.imageUrl, "drawable", requireContext().packageName)
            if (imageResourceId != 0) {
                binding.imageCard.setImageResource(imageResourceId)
            } else {
                Glide.with(this)
                    .load(it.imageUrl)
                    .into(binding.imageCard)
            }

            binding.textNumeroCarte.text = it.numeroCarte
            binding.textDateExpiration.text = it.dateExpiration
            binding.textUserName.text = it.userName

            lifecycleScope.launch{
                val currentCard= cardsConfigViewModel.getCurrentCard("${it.numeroCarte}")
                binding.switchGeneral.isChecked= currentCard?.configuration?.contactless!!

                binding.switchInternetMaroc.isChecked= currentCard?.configuration?.internetMaroc!!
                binding.switchPaiementTPE.isChecked= currentCard?.configuration?.tpeMaroc!!
                binding.switchRetraitMaroc.isChecked= currentCard?.configuration?.retraitMaroc!!
                binding.switchInternetEtranger.isChecked= currentCard?.configuration?.internetEtranger!!
                binding.switchTpeEtranger.isChecked= currentCard?.configuration?.tpeEtranger!!


            }
//            binding.switchGeneral.isChecked= it?.configuration?.contactless == true
//            lifecycleScope.launch{
//                val currentCard= cardsConfigViewModel.getCurrentCard("${it.numeroCarte}")
//                binding.switchGeneral.isChecked= currentCard?.configuration?.contactless == true
//            }

        }





        binding.switchGeneral.setOnClickListener { isChecked ->

            lifecycleScope.launch {
                try {
                    currentCardItem?.let {
                        cardsConfigViewModel.updateCardEtatForIdCard(it.numeroCarte, "contactless")
                    }

                } catch (e: Exception) {
                    println("Error updating card etat: ${e.message}")
                }
            }
        }
        binding.switchInternetMaroc.setOnClickListener { isChecked ->

            lifecycleScope.launch {
                try {
                    currentCardItem?.let {
                        cardsConfigViewModel.updateCardEtatForIdCard(it.numeroCarte, "internetMaroc")
                    }

                } catch (e: Exception) {
                    println("Error updating card etat: ${e.message}")
                }
            }
        }
        binding.switchPaiementTPE.setOnClickListener { isChecked ->

            lifecycleScope.launch {
                try {
                    currentCardItem?.let {
                        cardsConfigViewModel.updateCardEtatForIdCard(it.numeroCarte, "tpeMaroc")
                    }

                } catch (e: Exception) {
                    println("Error updating card etat: ${e.message}")
                }
            }
        }
        binding.switchRetraitMaroc.setOnClickListener { isChecked ->

            lifecycleScope.launch {
                try {
                    currentCardItem?.let {
                        cardsConfigViewModel.updateCardEtatForIdCard(it.numeroCarte, "retraitMaroc")
                    }

                } catch (e: Exception) {
                    println("Error updating card etat: ${e.message}")
                }
            }
        }
        binding.switchInternetEtranger.setOnClickListener { isChecked ->

            lifecycleScope.launch {
                try {
                    currentCardItem?.let {
                        cardsConfigViewModel.updateCardEtatForIdCard(it.numeroCarte, "internetEtranger")
                    }

                } catch (e: Exception) {
                    println("Error updating card etat: ${e.message}")
                }
            }
        }
        binding.switchTpeEtranger.setOnClickListener { isChecked ->

            lifecycleScope.launch {
                try {
                    currentCardItem?.let {
                        cardsConfigViewModel.updateCardEtatForIdCard(it.numeroCarte, "tpeEtranger")
                    }

                } catch (e: Exception) {
                    println("Error updating card etat: ${e.message}")
                }
            }
        }

    }



}
