package com.example.testoo.UI.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.testoo.UI.CardsManagement.PlafondsDetails.Tabs.DetailsPlafondsEtrangerFragment
import com.example.testoo.UI.CardsManagement.PlafondsDetails.Tabs.DetailsPlafondsMarocFragment
import com.example.testoo.UI.CardsManagement.TabLayouts.PlafondsEtrangerFragment
import com.example.testoo.UI.CardsManagement.TabLayouts.PlafondsMarocFragment


class DetailsPlafondsAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if(position == 0){
            DetailsPlafondsMarocFragment()
        }else{
            DetailsPlafondsEtrangerFragment()
        }
    }
}


