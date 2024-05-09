package com.example.testoo.UI.Payment.IAM

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testoo.Domain.models.Recharge
import com.example.testoo.R
import com.example.testoo.UI.Adapters.OptionsAdapter
import com.example.testoo.UI.Adapters.RechargeListAdapter
import com.example.testoo.Utils.BottomNavBarHandler
import com.example.testoo.databinding.FragmentSelectionRechargeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SelectionRechargeFragment : Fragment() , RechargeListAdapter.OnRechargeClickListener {

    private lateinit var binding : FragmentSelectionRechargeBinding

    private var rechargeRecyclerView : RecyclerView? =null
    private var rechargeRecyclerViewAdapter : RecyclerView.Adapter<*>? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSelectionRechargeBinding.inflate(layoutInflater)
        initRechargeRecyclerView("10,00")

        val circularProgressBar = binding.
        circularProgressBar.apply {
            progress = 33f
//            setProgressWithAnimation(25f, 1000)
            progressMax = 100f
            progressBarColor = Color.BLACK
            progressBarColorStart = Color.GRAY
            progressBarColorEnd = Color.YELLOW
            backgroundProgressBarColor = Color.GRAY
            backgroundProgressBarColorStart = Color.WHITE
            backgroundProgressBarColorEnd = Color.YELLOW
            roundBorder = true
        }

        circularProgressBar.setProgressWithAnimation(33f, 3000)

        lifecycleScope.launch {
            var progress = 0f
            while (progress <= 33f) {
                delay(60)
                progress += 1f
                circularProgressBar.setProgressWithAnimation(progress, 100)
            }
        }


        binding.buttonContinue.setOnClickListener {

//            activity?.supportFragmentManager?.beginTransaction()
//                ?.replace(R.id.fragment_container, SelectionRechargeFragment2())
//                ?.addToBackStack(null)
//                ?.commit()
            Navigation.findNavController(binding.root).navigate(R.id.action_selectionRechargeFragment_to_selectionRechargeFragment2)

        }








        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? BottomNavBarHandler)?.setUpBottomNavBar()

    }


    private fun initRechargeRecyclerView(montantRecharge : String){
        val recharges : ArrayList<Recharge> = ArrayList<Recharge>()
        recharges.add(Recharge("0","Recharge Normale",montantRecharge))
        recharges.add(Recharge("1","Pass SMS *1",montantRecharge))
        recharges.add(Recharge("2","Pass National et Internet *2",montantRecharge))
        recharges.add(Recharge("3","Pass National *22",montantRecharge))
        recharges.add(Recharge("4","Pass Internet *3",montantRecharge))
        recharges.add(Recharge("5","Pass Tout en un *5",montantRecharge))
        recharges.add(Recharge("6","Pass Reseaux Soiaux *6",montantRecharge))
        recharges.add(Recharge("7","Pass Premium *9",montantRecharge))
        recharges.add(Recharge("8","Pass Gaming *88",montantRecharge))

        rechargeRecyclerViewAdapter = RechargeListAdapter(recharges)

        (rechargeRecyclerViewAdapter as RechargeListAdapter).setOnRechargeClickListener(this@SelectionRechargeFragment)

        rechargeRecyclerView = binding.viewRecycler



        rechargeRecyclerView?.layoutManager =
            LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)
        rechargeRecyclerView?.adapter = rechargeRecyclerViewAdapter



    }

    override fun onRechargeClicked(recharge: Recharge) {
        println("this is the recharge: $recharge")

    }


}
