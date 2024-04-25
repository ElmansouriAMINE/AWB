package com.example.testoo.UI.AgencesFragments

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.testoo.Data.remote.Dto.AgenceWafaCashDto
import com.example.testoo.Domain.Repository.WafaCashRepository
import com.example.testoo.R
import com.example.testoo.UI.DialogFragments.MarkerInfoFragment
import com.example.testoo.ViewModels.WafaCashViewModel
import com.example.testoo.databinding.FragmentMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MapsFragment : Fragment(),GoogleMap.OnMarkerClickListener {

    @Inject
    lateinit var wafaCashRepository: WafaCashRepository

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    private lateinit var binding: FragmentMapsBinding

//    private lateinit var viewModel: WafaCashViewModel
    private lateinit var googleMap: GoogleMap
    val viewModel: WafaCashViewModel by viewModels()
    private val markerToAgenceMap: MutableMap<Marker?, AgenceWafaCashDto> = mutableMapOf()






    private val callback = OnMapReadyCallback { googleMap ->
//
//        val casablanca = LatLng(33.589886, -7.603869)
//        val cameraPosition = CameraPosition.Builder()
//            .target(casablanca)
//            .zoom(12f) // Set the zoom level
//            .build()
//
//        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

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
            setupMap()
//            val casablanca = LatLng(33.5731104, -7.603869)
//            googleMap.addMarker(MarkerOptions().position(casablanca).title("Marker in Casablanca"))
            googleMap.setOnMarkerClickListener(this@MapsFragment)

//            googleMap.setOnCameraIdleListener {
//                val cameraPosition = googleMap.cameraPosition.target
//                val distance = calculateDistance(currentLatLng, cameraPosition)
//                if (distance > 100) {
//                    // Mettre à jour les agences les plus proches
//                    currentLatLng = cameraPosition
//                    // Mettre à jour les agences affichées
//                    updateNearbyAgencies(cameraPosition)
//                }
//            }
//            val cameraPosition = CameraPosition.Builder()
//                .target(casablanca)
//                .zoom(14f)
//                .build()

//            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(casablanca))
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


//            viewModel.agencesWafaCashState
//                .onEach { state ->
//                println("Ptest" + state.agencesWafaCashList.toString())
//                if (!state.isLoading && state.agencesWafaCashList.isNotEmpty()) {
//                    state.agencesWafaCashList.forEach { agence->
//                            val marker = MarkerOptions()
//                                .position(LatLng(agence.latitude.toDouble(), agence.longitude.toDouble()))
//                                .title(agence.nom)
//                            println("hhhhhh" + agence.nom)
//                            googleMap.addMarker(marker)
//                        }
//
//
//                } else if (state.error.isNotEmpty()) {
//                    println("ERRRRRRRRRRRRRROR")
//                }
//            }.launchIn(viewLifecycleOwner.lifecycleScope)



        }
        viewLifecycleOwner.lifecycleScope.launch {
            val agences = wafaCashRepository.getAgencesWafacashNear()

            print("agencessss:"+agences)

        }
        binding.itemAgences.setOnClickListener {
            animateTextView(binding.itemAgences)
            binding.itemGABs.setBackgroundResource(R.drawable.tab_back)
            moveSelectTextView(binding.itemAgences)


        }

        binding.itemGABs.setOnClickListener {
            animateTextView(binding.itemGABs)
            moveSelectTextView(binding.itemGABs)
            binding.itemGABs.setBackgroundResource(R.drawable.tab_select)

        }

        binding.itemWafacaches.setOnClickListener {
            animateTextView(binding.itemWafacaches)
            binding.itemGABs.setBackgroundResource(R.drawable.tab_back)
            moveSelectTextView(binding.itemWafacaches)

            viewModel.agencesWafaCashState
                .onEach { state ->
                    println("Ptest" + state.agencesWafaCashList.toString())
                    if (!state.isLoading && state.agencesWafaCashList.isNotEmpty()) {
                        state.agencesWafaCashList.forEach { agence->
                            val marker = MarkerOptions()
                                .position(LatLng(agence.latitude.toDouble(), agence.longitude.toDouble()))
                                .title(agence.nom)
                            println("hhhhhh" + agence.nom)
//                            val customInfoWindowAdapter = CustomInfomMarkerAdapter(requireContext(),agence)
//                            googleMap.setInfoWindowAdapter(customInfoWindowAdapter)
//                            addWafaCashMarker(agence)
                            googleMap.addMarker(marker)

                        }


                    } else if (state.error.isNotEmpty()) {
                        println("ERRRRRRRRRRRRRROR")
                    }
                }.launchIn(viewLifecycleOwner.lifecycleScope)


        }


    }

    private fun addWafaCashMarker(agence: AgenceWafaCashDto) {
        val markerInfoFragment = MarkerInfoFragment()
        markerInfoFragment.setAgence(agence)
        markerInfoFragment.show(childFragmentManager, "markerInfoFragment")
    }

    private fun calculateDistance(start: LatLng, end: LatLng): Float {
        val results = FloatArray(1)
        Location.distanceBetween(start.latitude, start.longitude, end.latitude, end.longitude, results)
        return results[0]
    }

    @RequiresApi(34)
    private fun updateNearbyAgencies(center: LatLng) {
        val maxDistance = 500
        viewModel.agencesWafaCashState
            .onEach { state ->
                if (!state.isLoading && state.agencesWafaCashList.isNotEmpty()) {
                    googleMap.clear()
                    markerToAgenceMap.clear()
                    state.agencesWafaCashList.forEach { agence ->
                        val agenceLatLng = LatLng(agence.latitude.toDouble(), agence.longitude.toDouble())
                        val distance = calculateDistance(center, agenceLatLng)
                        if (distance <= maxDistance) {
                            val marker = MarkerOptions()
                                .position(agenceLatLng)
                                .title(agence.nom)
//                            val customInfoWindowAdapter = CustomInfomMarkerAdapter(requireContext(),agence)
//                            googleMap.setInfoWindowAdapter(customInfoWindowAdapter)
//                            addWafaCashMarker(agence)
                            val addedMarker = googleMap.addMarker(marker)
                            markerToAgenceMap[addedMarker] = agence
//                            googleMap.addMarker(marker)
                        }
                    }
                } else if (state.error.isNotEmpty()) {
                    println("ERRRRRRRRRRRRRROR")
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }



    private fun moveSelectTextView(targetView: View) {
        val selectTextView = binding.select
        val targetX = targetView.x
        selectTextView.animate()
            .x(targetX)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
    }

//    private fun setupMap() {
//        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            googleMap.isMyLocationEnabled = true
//            googleMap.uiSettings.isMyLocationButtonEnabled = true
//        } else {
//            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
//        }
//    }
    @RequiresApi(34)
    @SuppressLint("MissingPermission")
    private fun setupMap() {
        val fineLocationPermissionGranted = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarseLocationPermissionGranted = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        if (fineLocationPermissionGranted && coarseLocationPermissionGranted) {
            googleMap.isMyLocationEnabled= true
            googleMap.uiSettings.isMyLocationButtonEnabled = true
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        var currentLatLng = LatLng(location.latitude, location.longitude)
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 14f))
                        googleMap.setOnCameraIdleListener {
                            val cameraPosition = googleMap.cameraPosition.target
                            val distance = calculateDistance(currentLatLng, cameraPosition)
                            if (distance > 100) {
                                currentLatLng = cameraPosition
                                updateNearbyAgencies(cameraPosition)
                            }
                        }
                    }
                }

        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        }
    }



    @RequiresApi(34)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setupMap()
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val agence = markerToAgenceMap[marker]
        if (agence != null) {
            addWafaCashMarker(agence)
        }
        return true
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

