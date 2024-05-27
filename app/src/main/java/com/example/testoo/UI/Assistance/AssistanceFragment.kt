package com.example.testoo.UI.Assistance

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
import com.example.testoo.UI.Adapters.FragmentPageAdapter
import com.example.testoo.UI.Adapters.FragmentPageAssistanteAdapter
import com.example.testoo.ViewModels.CardsConfigViewModel
import com.example.testoo.databinding.FragmentAssistanceBinding
import com.example.testoo.databinding.FragmentModifierPlafondsBinding
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch


class AssistanceFragment : Fragment() {

    private lateinit var binding: FragmentAssistanceBinding

    private lateinit var adapter: FragmentPageAssistanteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentAssistanceBinding.inflate(layoutInflater)
        adapter = FragmentPageAssistanteAdapter(childFragmentManager, lifecycle)
        setupViewPagerAndTabs()

        binding.backArrow.setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }

        return binding.root
    }





    private fun setupViewPagerAndTabs() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("National"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("International"))
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




}

