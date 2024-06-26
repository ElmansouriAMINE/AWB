package com.example.testoo.UI.AgencesFragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.testoo.Data.remote.Dto.AgenceAttijariWafaDto
import com.example.testoo.Data.remote.Dto.AgenceWafaCashDto
import com.example.testoo.Data.remote.Dto.GabDto
import com.example.testoo.Domain.Repository.WafaCashRepository
import com.example.testoo.R
import com.example.testoo.UI.DialogFragments.MarkerAttijariWafaInfoFragment
import com.example.testoo.UI.DialogFragments.MarkerGabInfoFragment
import com.example.testoo.UI.DialogFragments.MarkerInfoFragment
import com.example.testoo.Utils.BottomNavBarHandler
import com.example.testoo.ViewModels.AttijariwafaViewModel
import com.example.testoo.ViewModels.GabViewModel
import com.example.testoo.ViewModels.WafaCashViewModel
import com.example.testoo.databinding.FragmentMapsBinding
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*


import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import com.tapadoo.alerter.Alerter.Companion.create
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
    private val LOCATION_PERMISSION_REQUEST_CODE = 1000
    private val REQUEST_CHECK_SETTINGS = 2000



    private lateinit var binding: FragmentMapsBinding

    //    private lateinit var viewModel: WafaCashViewModel
    private lateinit var googleMap: GoogleMap
    val viewModel: WafaCashViewModel by viewModels()
    private val markerToAgenceMap: MutableMap<Marker?, AgenceWafaCashDto> = mutableMapOf()

    val attijariViewModel: AttijariwafaViewModel by viewModels()

    private val markerToAttijariWafaMap : MutableMap<Marker?,AgenceAttijariWafaDto> = mutableMapOf()


    //ADD
    val gabViewModel: GabViewModel by viewModels()
    private val markerToGabMap : MutableMap<Marker?,GabDto> = mutableMapOf()






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
        (requireActivity() as? BottomNavBarHandler)?.setUpBottomNavBar()
//        viewModel = ViewModelProvider(requireActivity()).get(WafaCashViewModel::class.java)

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {


                val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                } else {

//                    showEnableGPSDialog()
                    print("enabling gps...")
                }
            } else {
                //
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )




        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync { googleMap ->
            this.googleMap = googleMap
            googleMap.setOnMarkerClickListener(this@MapsFragment)
            getUserCurrentLocation()
            binding.itemAgences.performClick()

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



        }

        viewLifecycleOwner.lifecycleScope.launch {
            val agences = wafaCashRepository.getAgencesWafacashNear()

            print("agencessss:"+agences)

        }

        binding.myLocationButton.setOnClickListener {
//            val casablanca = LatLng(33.589886, -7.603869)
//            val cameraPosition = CameraPosition.Builder()
//                .target(casablanca)
//                .zoom(12f) // Set the zoom level
//                .build()
//            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

            getUserCurrentLocation()

//            setupMap()
        }
        binding.itemAgences.setOnClickListener {
            animateTextView(binding.itemAgences)
            binding.itemGABs.setBackgroundResource(R.drawable.tab_back)
            moveSelectTextView(binding.itemAgences)
            binding.itemAgences.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            binding.itemGABs.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            binding.itemWafacaches.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            setupMapAttijariWafa()

            attijariViewModel.agenceAttijariWafaState
                .onEach { state ->
                    println("Ptest" + state.agencesAttijariWafaList.toString())
                    if (!state.isLoading && state.agencesAttijariWafaList.isNotEmpty()) {
                        googleMap.clear()
                        state.agencesAttijariWafaList.forEach { agenceWafa->
                            val marker = MarkerOptions()
                                .position(LatLng(agenceWafa.latitude.toDouble(), agenceWafa.longitude.toDouble()))
                                .title(agenceWafa.nom)
                            println("hhhhhh" + agenceWafa.nom)
//                            val customInfoWindowAdapter = CustomInfomMarkerAdapter(requireContext(),agence)
//                            googleMap.setInfoWindowAdapter(customInfoWindowAdapter)
//                            addWafaCashMarker(agence)
                            googleMap.addMarker(marker)
                            val addedMarker = googleMap.addMarker(marker)
                            markerToAttijariWafaMap[addedMarker] = agenceWafa

                        }


                    } else if (state.error.isNotEmpty()) {
                        println("ERRRRRRRRRRRRRROR")
                    }
                }.launchIn(viewLifecycleOwner.lifecycleScope)




        }

        binding.itemGABs.setOnClickListener {
            animateTextView(binding.itemGABs)
            moveSelectTextView(binding.itemGABs)
            binding.itemGABs.setBackgroundResource(R.drawable.tab_select)
            binding.itemAgences.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            binding.itemGABs.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            binding.itemWafacaches.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            setupMapGabs()


            gabViewModel.gabsState
                .onEach { state ->
                    println("Ptest" + state.gabsList.toString())
                    if (!state.isLoading && state.gabsList.isNotEmpty()) {
                        googleMap.clear()
                        state.gabsList.forEach { gab->
                            val marker = MarkerOptions()
                                .position(LatLng(gab.latitude.toDouble(), gab.longitude.toDouble()))
                                .title(gab.nom)
                            println("hhhhhh" + gab.nom)
//                            val customInfoWindowAdapter = CustomInfomMarkerAdapter(requireContext(),agence)
//                            googleMap.setInfoWindowAdapter(customInfoWindowAdapter)
//                            addWafaCashMarker(agence)
                            googleMap.addMarker(marker)
                            val addedMarker = googleMap.addMarker(marker)
                            markerToGabMap[addedMarker] = gab

                        }


                    } else if (state.error.isNotEmpty()) {
                        println("ERRRRRRRRRRRRRROR")
                    }
                }.launchIn(viewLifecycleOwner.lifecycleScope)


        }

        binding.itemWafacaches.setOnClickListener {
            animateTextView(binding.itemWafacaches)
            binding.itemGABs.setBackgroundResource(R.drawable.tab_back)
            moveSelectTextView(binding.itemWafacaches)
            binding.itemAgences.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            binding.itemGABs.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            binding.itemWafacaches.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            setupMapWafaCash()

            viewModel.agencesWafaCashState
                .onEach { state ->
                    println("Ptest" + state.agencesWafaCashList.toString())
                    if (!state.isLoading && state.agencesWafaCashList.isNotEmpty()) {
                        googleMap.clear()
                        state.agencesWafaCashList.forEach { agence->
                            val marker = MarkerOptions()
                                .position(LatLng(agence.latitude.toDouble(), agence.longitude.toDouble()))
                                .title(agence.nom)
                            println("hhhhhh" + agence.nom)
//                            val customInfoWindowAdapter = CustomInfomMarkerAdapter(requireContext(),agence)
//                            googleMap.setInfoWindowAdapter(customInfoWindowAdapter)
//                            addWafaCashMarker(agence)
                            googleMap.addMarker(marker)
                            val addedMarker = googleMap.addMarker(marker)
                            markerToAgenceMap[addedMarker] = agence

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

    private fun addAttijariwafaMarker(agence: AgenceAttijariWafaDto) {
        val markerAttijariWafaInfoFragment = MarkerAttijariWafaInfoFragment()
        markerAttijariWafaInfoFragment.setAgence(agence)
        markerAttijariWafaInfoFragment.show(childFragmentManager, "markerAttijariWafaInfoFragment")
    }

    private fun addGabMarker(gab:GabDto){
        val markerGabInfoFragment = MarkerGabInfoFragment()
        markerGabInfoFragment.setGab(gab)
        markerGabInfoFragment.show(childFragmentManager,"markerGabInfoFragment")

    }

    private fun calculateDistance(start: LatLng, end: LatLng): Float {
        val results = FloatArray(1)
        Location.distanceBetween(start.latitude, start.longitude, end.latitude, end.longitude, results)
        return results[0]
    }

    @RequiresApi(34)
    private fun updateNearbyAgencies(center: LatLng) {
        val maxDistance = 500
        gabViewModel.gabsState.onEach { state ->
            if (!state.isLoading && state.gabsList.isNotEmpty()) {
                googleMap.clear()
//                markerToGabMap.clear()
                state.gabsList.forEach { gab ->
                    val gabLatLng = LatLng(gab.latitude.toDouble(), gab.longitude.toDouble())
                    val distance = calculateDistance(center, gabLatLng)
                    if (distance <= maxDistance) {
//                        val icon = BitmapDescriptorFactory.fromResource(R.drawable.attijariwafa)
                        val marker = MarkerOptions()
                            .position(gabLatLng)
                            .title(gab.nom)
//                            .icon(icon)
//                            val customInfoWindowAdapter = CustomInfomMarkerAdapter(requireContext(),agence)
//                            googleMap.setInfoWindowAdapter(customInfoWindowAdapter)
//                            addWafaCashMarker(agence)
                        val addedMarker = googleMap.addMarker(marker)
                        markerToGabMap[addedMarker] = gab
                        print("gabi:$gab")
                        print("PPPP:${markerToGabMap[addedMarker]}")
//                            googleMap.addMarker(marker)
                    }
                }
            } else if (state.error.isNotEmpty()) {
                println("ERRRRRRRRRRRRRROR")
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.agencesWafaCashState
            .onEach { state ->
                if (!state.isLoading && state.agencesWafaCashList.isNotEmpty()) {
                    googleMap.clear()
                    markerToAgenceMap.clear()
                    state.agencesWafaCashList.forEach { agence ->
                        val agenceLatLng = LatLng(agence.latitude.toDouble(), agence.longitude.toDouble())
                        val distance = calculateDistance(center, agenceLatLng)
                        if (distance <= maxDistance) {
//                            val icon = BitmapDescriptorFactory.fromResource(R.drawable.location_icon_red)
                            val marker = MarkerOptions()
                                .position(agenceLatLng)
                                .title(agence.nom)
//                                .icon(icon)
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
    private fun showEnableGPSDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Enable GPS")
            .setMessage("GPS is required for requireContext() feature. Do you want to enable it now?")
            .setPositiveButton("Yes") { _, _ ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
                // Handle if the user chooses not to enable GPS
            }
            .show()
    }


    //ajout

    @RequiresApi(34)
    private fun updateNearbyAgenciesWafacash(center: LatLng) {
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
//                            val icon = BitmapDescriptorFactory.fromResource(R.drawable.location_icon_red)
                            val marker = MarkerOptions()
                                .position(agenceLatLng)
                                .title(agence.nom)
//                                .icon(icon)
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

    @RequiresApi(34)
    private fun updateNearbyAgenciesAttijariwafa(center: LatLng) {
        val maxDistance = 500
        attijariViewModel.agenceAttijariWafaState
            .onEach { state ->
                if (!state.isLoading && state.agencesAttijariWafaList.isNotEmpty()) {
                    googleMap.clear()
                    markerToAttijariWafaMap.clear()
                    state.agencesAttijariWafaList.forEach { agence ->
                        val agenceLatLng = LatLng(agence.latitude.toDouble(), agence.longitude.toDouble())
                        val distance = calculateDistance(center, agenceLatLng)
                        if (distance <= maxDistance) {
//                            val icon = BitmapDescriptorFactory.fromResource(R.drawable.location_icon_red)
                            val marker = MarkerOptions()
                                .position(agenceLatLng)
                                .title(agence.nom)
//                                .icon(icon)
//                            val customInfoWindowAdapter = CustomInfomMarkerAdapter(requireContext(),agence)
//                            googleMap.setInfoWindowAdapter(customInfoWindowAdapter)
//                            addWafaCashMarker(agence)
                            val addedMarker = googleMap.addMarker(marker)
                            markerToAttijariWafaMap[addedMarker] = agence
//                            googleMap.addMarker(marker)
                        }
                    }
                } else if (state.error.isNotEmpty()) {
                    println("ERRRRRRRRRRRRRROR")
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }



    @RequiresApi(34)
    private fun updateNearbyAgenciesGabs(center: LatLng) {
        val maxDistance = 500
        gabViewModel.gabsState.onEach { state ->
            if (!state.isLoading && state.gabsList.isNotEmpty()) {
                googleMap.clear()
//                markerToGabMap.clear()
                state.gabsList.forEach { gab ->
                    val gabLatLng = LatLng(gab.latitude.toDouble(), gab.longitude.toDouble())
                    val distance = calculateDistance(center, gabLatLng)
                    if (distance <= maxDistance) {
//                        val icon = BitmapDescriptorFactory.fromResource(R.drawable.attijariwafa)
                        val marker = MarkerOptions()
                            .position(gabLatLng)
                            .title(gab.nom)
//                            .icon(icon)
//                            val customInfoWindowAdapter = CustomInfomMarkerAdapter(requireContext(),agence)
//                            googleMap.setInfoWindowAdapter(customInfoWindowAdapter)
//                            addWafaCashMarker(agence)
                        val addedMarker = googleMap.addMarker(marker)
                        markerToGabMap[addedMarker] = gab
                        print("gabi:$gab")
                        print("PPPP:${markerToGabMap[addedMarker]}")
//                            googleMap.addMarker(marker)
                    }
                }
            } else if (state.error.isNotEmpty()) {
                println("ERRRRRRRRRRRRRROR")
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    @RequiresApi(34)
    @SuppressLint("MissingPermission")
    private fun setupMapGabs() {
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
                                updateNearbyAgenciesGabs(cameraPosition)
                            }
                        }
                    }
                }

        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    @RequiresApi(34)
    @SuppressLint("MissingPermission")
    private fun setupMapAttijariWafa() {
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
                                updateNearbyAgenciesAttijariwafa(cameraPosition)
                            }
                        }
                    }
                }

        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        }
    }


    @RequiresApi(34)
    @SuppressLint("MissingPermission")
    private fun setupMapWafaCash() {
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
                                updateNearbyAgenciesWafacash(cameraPosition)
                            }
                        }
                    }
                }

        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        }
    }





    //fin ajout



    private fun moveSelectTextView(targetView: View) {
        val selectTextView = binding.select
        val targetX = targetView.x
        selectTextView.animate()
            .x(targetX)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
    }


//    private fun requestLocationPermissions() {
//        ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
//    }
    private fun requestLocationPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(requireActivity(), arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            // Permissions are already granted
            enableGPS()
        }
    }

    @RequiresApi(34)
    @SuppressLint("MissingPermission")
    private fun getUserCurrentLocation() {
        val fineLocationPermissionGranted = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarseLocationPermissionGranted = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        if (fineLocationPermissionGranted && coarseLocationPermissionGranted) {
            val locationRequest = LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
            val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

            val client: SettingsClient = LocationServices.getSettingsClient(requireContext())
            val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

            task.addOnSuccessListener { locationSettingsResponse ->
                // All location settings are satisfied. The client can initialize location requests here.
                enableLocationUpdates()
            }

            task.addOnFailureListener { exception ->
                if (exception is ApiException) {
                    when (exception.statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                            // Here we send the request to enable the GPS
                            val status = exception.status
                            if (status.hasResolution()) {
                                status.resolution?.intentSender?.let { intentSender ->
                                    val intentSenderRequest = IntentSenderRequest.Builder(intentSender).build()
                                    resolutionGpsLauncher.launch(intentSenderRequest)
                                }
                            } else {
                                showFailurePopup()
                            }
                        } catch (sendIntentException: IntentSender.SendIntentException) {
//                            ABLogger.e(TAG, "SendIntentException")
                            showFailurePopup()
                        }

                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
//                            ABLogger.e(TAG, "SETTINGS_CHANGE_UNAVAILABLE")
                            showFailurePopup()
                        }

                        else -> showFailurePopup()
                    }
                }
            }
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    @SuppressLint("MissingPermission")
    private fun enableLocationUpdates() {
        googleMap.isMyLocationEnabled = true
        googleMap.uiSettings.isMyLocationButtonEnabled = false
        googleMap.uiSettings.isCompassEnabled = false
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 14f))
                }
            }
    }

//    private fun showFailurePopup() {
//        // Replace with your actual failure handling logic
//        abPopupFactory.showFailurePopup(
//            tag = this.javaClass.name,
//            activity = requireActivity(),
//            response = response
//        )
//    }
    private fun showFailurePopup() {
        Toast.makeText(requireContext(), "Failed to enable location settings", Toast.LENGTH_SHORT).show()
    }

    private val resolutionGpsLauncher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            enableLocationUpdates()
        } else {
            // Handle the case where the user did not enable GPS
            showFailurePopup()
        }
    }



//    @RequiresApi(34)
//    @SuppressLint("MissingPermission")
//    private fun getUserCurrentLocation() {
//        val fineLocationPermissionGranted = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//        val coarseLocationPermissionGranted = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
//        if (fineLocationPermissionGranted && coarseLocationPermissionGranted) {
//            googleMap.isMyLocationEnabled= true
//            googleMap.uiSettings.isMyLocationButtonEnabled = false
//            googleMap.uiSettings.isCompassEnabled=false
//            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
//            fusedLocationClient.lastLocation
//                .addOnSuccessListener { location ->
//                    if (location != null) {
//                        var currentLatLng = LatLng(location.latitude, location.longitude)
//                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 14f))
//
//                    }
//                }
//
//        } else {
//            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
//        }
//    }



//    @RequiresApi(34)
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////            setupMap()
//        }
//    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Permission granted
                enableGPS()
            } else {
                // Permission denied
                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                // Use the location object
                val latitude = location.latitude
                val longitude = location.longitude
                // Do something with the location
            } else {
                // Handle location not found
                Toast.makeText(requireContext(), "Location not found", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun enableGPS() {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(requireContext())
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener { locationSettingsResponse ->
            // All location settings are satisfied. The client can initialize location requests here.
            getCurrentLocation()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but requireContext() can be fixed by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result in onActivityResult().
                    exception.startResolutionForResult(requireActivity(), REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                // GPS is enabled
                getCurrentLocation()
            } else {
                // The user did not enable GPS
                Toast.makeText(requireContext(), "GPS not enabled", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onMarkerClick(marker: Marker): Boolean {
        val agenceAttijariWafa= markerToAttijariWafaMap[marker]
        val gab = markerToGabMap[marker]
        print("markerToGAB :${markerToGabMap[marker]}")
        val agence = markerToAgenceMap[marker]
        print("markerToAgence :${markerToAgenceMap[marker]}")
        if (agence != null) {
            addWafaCashMarker(agence)
        }
        if (gab != null) {
            print("THERISAGAB")

            addGabMarker(gab)
        }

        if(agenceAttijariWafa !=null){
            addAttijariwafaMarker(agenceAttijariWafa)
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
