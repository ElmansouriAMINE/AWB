package com.example.testoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.testoo.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setUpTabBar()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,SignInFragment())
            .commit()

    }

    private fun setUpTabBar() {
        binding.bottomNavBar.setOnItemSelectedListener{
            when(it){
                R.id.icon_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, SignInFragment())
                        .commit()
                    true
                }
                R.id.icon_location -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, LocationFragment())
                        .commit()
                    true
                }
                R.id.icon_message-> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MessageFragment())
                        .commit()
                    true
                }
                R.id.icon_person -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ProfileFragment())
                        .commit()
                    true
                }
            }
        }
    }
}
