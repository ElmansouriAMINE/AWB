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
import com.example.testoo.databinding.FragmentOppositionCarte2Binding
import com.example.testoo.databinding.FragmentOppositionCarteBinding
import kotlinx.coroutines.launch


class OppositionCarteFragment2 : Fragment() {

    private lateinit var binding: FragmentOppositionCarte2Binding

    private val cardsConfigViewModel by activityViewModels<CardsConfigViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOppositionCarte2Binding.inflate(layoutInflater)




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


    }


}
