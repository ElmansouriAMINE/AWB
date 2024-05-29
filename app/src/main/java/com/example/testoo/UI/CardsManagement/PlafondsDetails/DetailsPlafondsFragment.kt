package com.example.testoo.UI.CardsManagement.PlafondsDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.testoo.R
import com.example.testoo.UI.Adapters.DetailsPlafondsAdapter
import com.example.testoo.UI.Adapters.FragmentPageAdapter
import com.example.testoo.ViewModels.CardsConfigViewModel
import com.example.testoo.databinding.FragmentDetailsPlafondsBinding
import com.example.testoo.databinding.FragmentModifierPlafondsBinding
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch


class DetailsPlafondsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsPlafondsBinding
    private val cardsConfigViewModel by activityViewModels<CardsConfigViewModel>()
    private lateinit var adapter: DetailsPlafondsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsPlafondsBinding.inflate(inflater, container, false)
        setupCurrentCardInfo()

        adapter = DetailsPlafondsAdapter(childFragmentManager, lifecycle)
        setupViewPagerAndTabs()

        binding.backArrow.setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }

        return binding.root
    }



    private fun setupCurrentCardInfo() {
        val currentCardItem = cardsConfigViewModel.currentCardItem.value
        currentCardItem?.let {
            val imageResourceId = resources.getIdentifier(it.imageUrl, "drawable", requireContext().packageName)
            if (imageResourceId != 0) {
                binding.imageCard.setImageResource(imageResourceId)
            } else {
                Glide.with(this).load(it.imageUrl).into(binding.imageCard)
            }
            binding.textNumeroCarte.text = it.numeroCarte
            binding.textDateExpiration.text = it.dateExpiration
            binding.textUserName.text = it.userName
        }
    }

    private fun setupViewPagerAndTabs() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Plafonds au Maroc"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Plafonds à l'étranger"))
        binding.viewPager2.adapter = adapter

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let { binding.viewPager2.currentItem = it.position }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })
    }

    private fun updatePlafonds() {
        lifecycleScope.launch {
            try {
                val currentCardItem = cardsConfigViewModel.currentCardItem.value
                currentCardItem?.let {
                    val retraitMaroc = cardsConfigViewModel.retraitMaroc.value
                    val internetMaroc = cardsConfigViewModel.internetMaroc.value
                    val tpeMaroc = cardsConfigViewModel.tpeMaroc.value

                    if ((retraitMaroc != null)) {
                        cardsConfigViewModel.updatePlafondValueForIdCard(it.numeroCarte, "retraitMaroc", retraitMaroc!!)
                        Toast.makeText(requireContext(), "Plafonds updated successfully", Toast.LENGTH_SHORT).show()
                    }

                    if ((internetMaroc != null)) {
                        cardsConfigViewModel.updatePlafondValueForIdCard(it.numeroCarte, "internetMaroc", internetMaroc)
                        Toast.makeText(requireContext(), "Plafonds updated successfully", Toast.LENGTH_SHORT).show()
                    }

                    if ((tpeMaroc != null)) {
                        cardsConfigViewModel.updatePlafondValueForIdCard(it.numeroCarte, "tpeMaroc", tpeMaroc)
                        Toast.makeText(requireContext(), "Plafonds updated successfully", Toast.LENGTH_SHORT).show()
                    }

                    if ((retraitMaroc != null && internetMaroc != null && tpeMaroc != null)) {
                        cardsConfigViewModel.updatePlafondValueForIdCard(it.numeroCarte, "retraitMaroc", retraitMaroc)
                        cardsConfigViewModel.updatePlafondValueForIdCard(it.numeroCarte, "internetMaroc", internetMaroc)
                        cardsConfigViewModel.updatePlafondValueForIdCard(it.numeroCarte, "tpeMaroc", tpeMaroc)
                        Toast.makeText(requireContext(), "Plafonds updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Failed to update plafonds: Values are null", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error updating plafonds: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        cardsConfigViewModel.resetValues()
    }


}

