package com.example.testoo.UI.CardsManagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.testoo.R
import com.example.testoo.UI.Adapters.FragmentPageAdapter
import com.example.testoo.ViewModels.CardsConfigViewModel
import com.example.testoo.databinding.FragmentModifierPlafondsBinding
import com.google.android.material.tabs.TabLayout


class ModifierPlafondsFragment : Fragment() {


    private lateinit var binding: FragmentModifierPlafondsBinding

    private val cardsConfigViewModel by activityViewModels<CardsConfigViewModel>()

    private lateinit var adapter : FragmentPageAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentModifierPlafondsBinding.inflate(layoutInflater)

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

        adapter = FragmentPageAdapter(childFragmentManager,lifecycle)

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Plafonds au Maroc"))

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Plafonds à l'étranger"))

        binding.viewPager2.adapter = adapter


        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    binding.viewPager2.currentItem = tab.position
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        }
        )

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })









        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}
