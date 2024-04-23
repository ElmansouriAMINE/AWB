package com.example.testoo.UI.AgencesFragments

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.testoo.Domain.Repository.WafaCashRepository
import com.example.testoo.R
import com.example.testoo.ViewModels.WafaCashViewModel
import com.example.testoo.databinding.FragmentLocationBinding
import com.example.testoo.databinding.FragmentMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MapsFragment : Fragment() {

    @Inject
    lateinit var wafaCashRepository: WafaCashRepository

    private lateinit var binding: FragmentMapsBinding

//    private lateinit var viewModel: WafaCashViewModel
    private lateinit var googleMap: GoogleMap
    val viewModel: WafaCashViewModel by viewModels()



    private val callback = OnMapReadyCallback { googleMap ->

//        val sydney = LatLng(-34.0, 151.0)
//        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapsBinding.inflate(layoutInflater)


        return binding.root
    }

    @RequiresApi(34)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel = ViewModelProvider(requireActivity()).get(WafaCashViewModel::class.java)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync { googleMap ->
            this.googleMap = googleMap

            val casablanca = LatLng(33.5731104, -7.603869)
            googleMap.addMarker(MarkerOptions().position(casablanca).title("Marker in Casablanca"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(casablanca))
            CoroutineScope(Dispatchers.IO).launch{
                viewModel.agencesWafaCashState.collectLatest{
                   if (it.isLoading){
                       println("IS LOADDDDDDDDDDING")
                   }else if (it.error.isNotBlank()){
                       println("An error has occured")
                   }else{
                       println("Data Loaded successfully")
                       println("qqq" + it.agencesWafaCashList)
                   }
                }

            }

            viewModel.agencesWafaCashState
                .onEach { state ->
                println("Ptest" + state.agencesWafaCashList.toString())
                if (!state.isLoading && state.agencesWafaCashList.isNotEmpty()) {
                    state.agencesWafaCashList.forEach { agence->
                            val marker = MarkerOptions()
                                .position(LatLng(agence.latitude.toDouble(), agence.longitude.toDouble()))
                                .title(agence.nom)
                            println("hhhhhh" + agence.nom)
                            googleMap.addMarker(marker)
                        }


                } else if (state.error.isNotEmpty()) {
                    println("ERRRRRRRRRRRRRROR")
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)



        }
        viewLifecycleOwner.lifecycleScope.launch {
            val agences = wafaCashRepository.getAgencesWafacashNear()

            print("agencessss:"+agences)

        }
        binding.itemAgences.setOnClickListener {
            animateTextView(binding.itemAgences)
            binding.itemGABs.setBackgroundResource(R.drawable.tab_back)
            moveSelectTextView(binding.itemAgences)

            // Handle click for itemAgences
        }

        binding.itemGABs.setOnClickListener {
            animateTextView(binding.itemGABs)
            moveSelectTextView(binding.itemGABs)
            binding.itemGABs.setBackgroundResource(R.drawable.tab_select)
            // Handle click for itemGABs
        }

        binding.itemWafacaches.setOnClickListener {
            animateTextView(binding.itemWafacaches)
            binding.itemGABs.setBackgroundResource(R.drawable.tab_back)
            moveSelectTextView(binding.itemWafacaches)

            // Handle click for itemWafacaches
        }


    }

    private fun moveSelectTextView(targetView: View) {
        val selectTextView = binding.select
        val targetX = targetView.x
        selectTextView.animate()
            .x(targetX)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
    }


}
private fun animateTextView(textView: TextView) {
    textView.animate()
        .scaleX(1.2f)
        .scaleY(1.2f)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .withEndAction {
            textView.animate()
                .scaleX(1.0f)
                .scaleY(1.0f)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .start()
        }
        .start()
}

