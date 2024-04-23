package com.example.testoo.UI.AgencesFragments

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.testoo.Domain.Repository.WafaCashRepository
import com.example.testoo.R
import com.example.testoo.ViewModels.WafaCashViewModel

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
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    @RequiresApi(34)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel = ViewModelProvider(requireActivity()).get(WafaCashViewModel::class.java)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync { googleMap ->
            this.googleMap = googleMap

            val casablanca = LatLng(33.5731104, -7.603869)
            googleMap.addMarker(MarkerOptions().position(casablanca).title("Marker in Sydney"))
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


//             Inside onViewCreated method
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
    }



}


