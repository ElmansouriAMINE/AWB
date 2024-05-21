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


//class CardConfigFragment : Fragment() {
//
//    private lateinit var binding : FragmentCardConfigBinding
//    private val cardsConfigViewModel by activityViewModels<CardsConfigViewModel>()
//    private val currentCardItem = cardsConfigViewModel.currentCardItem.value
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        binding = FragmentCardConfigBinding.inflate(layoutInflater)
//
//
//        currentCardItem?.let {
//
//            val imageResourceId = resources.getIdentifier(it.imageUrl, "drawable", requireContext().packageName)
//            if (imageResourceId != 0) {
//                binding.imageCard.setImageResource(imageResourceId)
//            } else {
//                Glide.with(this)
//                    .load(it.imageUrl)
//                    .into(binding.imageCard)
//            }
//
//            binding.textNumeroCarte.text = it.numeroCarte
//            binding.textDateExpiration.text = it.dateExpiration
//            binding.textUserName.text = it.userName
//        }
//
//
//
//
//
//
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        data class Configuration(
//            val contactless:Boolean? =false,
//            val internetMaroc:Boolean?=false,
//            val tpeMaroc:Boolean?=false,
//            val retraitMaroc:Boolean?=false,
//            val internetEtranger:Boolean?=false,
//            val tpeEtranger: Boolean ?=false
//        )
//
//        binding.switchGeneral.setOnCheckedChangeListener { _, isChecked ->
//
//            lifecycleScope.launch {
//                try {
//                    currentCardItem?.let {
//                        cardsConfigViewModel.updateCardEtatForIdCard(it.numeroCarte,"contactless")
//                    }
//
//                } catch (e: Exception) {
//                    println("Error updating card etat: ${e.message}")
//                }
//            }
//        }
//    }
//
//
//}


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
        }

        binding.switchGeneral.setOnCheckedChangeListener { _, isChecked ->

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
    }
}
