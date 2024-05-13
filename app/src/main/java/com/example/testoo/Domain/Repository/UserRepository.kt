package com.example.testoo.Domain.Repository

import com.example.testoo.Domain.models.Compte
import com.example.testoo.Domain.models.Facture
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

interface UserRepository {

    suspend fun getCompteForUserId(userId: String): Compte?

    suspend fun getFactureNonPayeForUserId(userId: String,idContrat: String) : List<Facture>

}
