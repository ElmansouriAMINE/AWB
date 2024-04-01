package com.example.testoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.testoo.databinding.ActivityMainBinding
import com.example.testoo.models.Carte
import com.example.testoo.models.Compte
import com.example.testoo.models.User
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createFirebaseCollections()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setUpTabBar()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,SignInFragment())
            .commit()

    }

    private fun createFirebaseCollections() {
        // Initialize Firebase
        val database = FirebaseDatabase.getInstance("https://awb-test-2eaa2-default-rtdb.firebaseio.com/")

        val usersRef = database.getReference("users")

        val compteRef = database.getReference("comptes")
        val carteRef = database.getReference("cartes")
        val compte1 = Compte(id = 1, numero = "123456", solde = 1000.0, dateOuverture = "01/01/2022")
        val compte2 = Compte(id = 2, numero = "789012", solde = 2000.0, dateOuverture = "01/02/2022")
        val carte1 = Carte(
            id = 1,
            numeroCarte = "1234 5678 9012 3456",
            codeSecret = "123",
            dateExpiration = "12/24"
        )
        val carte2 = Carte(
            id = 2,
            numeroCarte = "1004 1008 1222 0006",
            codeSecret = "036",
            dateExpiration = "02/28"
        )

        val user = User(
            userName = "Amine",
            email = "amine@gmail.com",
            phoneNumber = "1234567890",
            photoUrl = "https://example.com/photo.jpg",
            comptes = listOf(compte1,compte2),
            cartes = listOf(carte1,carte2)
        )

        val compteKey = compteRef.push().key
        compteKey?.let { compteRef.child(it).setValue(compte1) }
        val carteKey = carteRef.push().key
        carteKey?.let { carteRef.child(it).setValue(carte1) }

        val userKey = usersRef.push().key
        userKey?.let { usersRef.child(it).setValue(user) }





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
