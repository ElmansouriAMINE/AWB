package com.example.testoo.UI

import android.content.*
import android.net.ConnectivityManager
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.testoo.databinding.ActivityMainBinding
import com.example.testoo.Domain.models.Carte
import com.example.testoo.Domain.models.Compte
import com.example.testoo.Domain.models.User
import com.example.testoo.R
import com.example.testoo.UI.AgencesFragments.MapsFragment
import com.example.testoo.Utils.BottomNavBarHandler
import com.example.testoo.Utils.NetworkUtil
import com.example.testoo.Utils.showNoInternetDialog
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BottomNavBarHandler {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var networkReceiver: BroadcastReceiver
    private lateinit var sharedPreferences: SharedPreferences
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)



        setContentView(binding.root)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Disable night mode
        // Load the theme preference
        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE)
        val nightMode = sharedPreferences.getBoolean("night", false)
        setNightMode(nightMode)
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Set up the navigation controller
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView3) as NavHostFragment
        navController = navHostFragment.navController

        // Check the current user and set up the bottom navigation bar
        val currentUser = FirebaseAuth.getInstance().currentUser
        Log.d(TAG, "Current User: $currentUser")

        setUpBottomNavBar()


        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment -> {
                    binding.bottomNavBar.visibility = View.GONE
                    binding.bottomNavBarUserNonConnecte.visibility = View.GONE
                }
                R.id.mapsFragment ->{
                    binding.bottomNavBar.visibility = View.GONE
                    binding.bottomNavBarUserNonConnecte.visibility = View.GONE
                }
                else -> setUpBottomNavBar()
            }
        }
        networkReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (!NetworkUtil.isConnected(this@MainActivity)) {
                    showNoInternetDialog(this@MainActivity)
                }
            }
        }
    }

    private fun setNightMode(nightMode: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (nightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    private fun setUpTabBar() {
        binding.bottomNavBar.setOnItemSelectedListener { menuItem ->
            when (menuItem) {
                R.id.icon_home -> {
                    Log.d(TAG, "Navigating to LocationFragment")
                    navController.navigate(R.id.toLocationFragment)
                }
                R.id.icon_location -> {
                    Log.d(TAG, "Navigating to MapsFragment")
                    navController.navigate(R.id.toMapsFragment)
                }
                R.id.icon_message -> {
                    Log.d(TAG, "Navigating to AssistanceFragment")
                    navController.navigate(R.id.toAssistanceFragment)
                }
                R.id.icon_person -> {
                    Log.d(TAG, "Navigating to ProfileFragment")
                    navController.navigate(R.id.toProfileFragment)
                }
            }
            true
        }
    }

    private fun setUpTabBiginningBar() {
        binding.bottomNavBarUserNonConnecte.setOnItemSelectedListener { menuItem ->
            when (menuItem) {
                R.id.icon_home -> {
                    Log.d(TAG, "Navigating to SignInFragment")
                    navController.navigate(R.id.toSignInFragment)
                }
                R.id.icon_location -> {
                    Log.d(TAG, "Navigating to MapsFragment")
                    navController.navigate(R.id.toMapsFragment)
                }
                R.id.icon_assistance ->{
                    navController.navigate(R.id.toAssistanceFragment)
                }
            }
            true
        }
    }

    override fun setUpBottomNavBar() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            Log.d(TAG, "User is logged in. Setting up authenticated navigation bar.")
            setUpTabBar()
            binding.bottomNavBarUserNonConnecte.visibility = View.GONE
            binding.bottomNavBar.visibility = View.VISIBLE
        } else {
            Log.d(TAG, "No user logged in. Setting up unauthenticated navigation bar.")
            setUpTabBiginningBar()
            binding.bottomNavBar.visibility = View.GONE
            binding.bottomNavBarUserNonConnecte.visibility = View.VISIBLE
        }
    }
    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkReceiver)
    }


}
