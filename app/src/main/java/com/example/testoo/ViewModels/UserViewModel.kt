package com.example.testoo.ViewModels

import androidx.lifecycle.ViewModel
import com.example.testoo.models.Compte
import com.google.firebase.Firebase
import com.google.firebase.database.database
import kotlinx.coroutines.tasks.await

class UserViewModel : ViewModel() {

    suspend fun getCompteForUserId(userId: String): Compte? {
        val currentUserCompte = Firebase.database.reference.child("comptes")
        val query = currentUserCompte.orderByChild("userId").equalTo(userId)
        return try {
            val dataSnapshot = query.get().await()
            dataSnapshot.children.mapNotNull { it.getValue(Compte::class.java) }.firstOrNull()
        } catch (e: Exception) {
            null
        }
    }

}
