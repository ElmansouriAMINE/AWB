package com.example.testoo.Data.Repository

import com.example.testoo.Domain.Repository.UserRepository
import com.example.testoo.Domain.models.Compte
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(): UserRepository {
    override suspend fun getCompteForUserId(userId: String): Compte? {
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
