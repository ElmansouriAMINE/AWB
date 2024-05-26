package com.example.testoo.UI.CardsManagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.testoo.R
import com.example.testoo.ViewModels.CardsConfigViewModel
import com.example.testoo.databinding.FragmentChoixConfigCardBinding


class ChoixConfigCardFragment : Fragment() {

    private lateinit var binding: FragmentChoixConfigCardBinding

    private val cardsConfigViewModel by activityViewModels<CardsConfigViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentChoixConfigCardBinding.inflate(layoutInflater)

        val currentCardItem = cardsConfigViewModel.currentCardItem.value

        currentCardItem?.let {

            val imageResourceId = resources.getIdentifier(it.imageUrl, "drawable", requireContext().packageName)
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

        binding.backArrow.setOnClickListener {
              Navigation.findNavController(binding.root).navigateUp()
        }






        binding.configCard.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_choixConfigCardFragment_to_cardConfigFragment)
        }

        binding.modifierplafonds.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_choixConfigCardFragment_to_modifierPlafondsFragment)
        }

        binding.opposerCarte.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_choixConfigCardFragment_to_oppositionCarteFragment)
        }





        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        cardsConfigViewModel.resetValues()
    }


}
