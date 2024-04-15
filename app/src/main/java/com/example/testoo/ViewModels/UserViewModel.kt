package com.example.testoo.ViewModels

import androidx.lifecycle.ViewModel
import com.example.testoo.models.Compte
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import kotlinx.coroutines.tasks.await

class UserViewModel : ViewModel() {

    suspend fun getCompteForUserId(userId: String): Compte? {
        val currentUserCompte = FirebaseDatabase.getInstance().getReference("comptes")
        val query = currentUserCompte.orderByChild("userId").equalTo(userId)
        return try {
            val dataSnapshot = query.get().await()
            val compte = dataSnapshot.children.mapNotNull { it.getValue(Compte::class.java) }.firstOrNull()
            println("Compte: $compte")
            compte
        } catch (e: Exception) {
            println("Error fetching compte: ${e.message}")
            null
        }
    }


}
