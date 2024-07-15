package com.example.testoo.Domain.Repository

import com.example.testoo.Domain.models.Carte
import com.example.testoo.Domain.models.Compte
import com.example.testoo.Domain.models.Facture
import com.example.testoo.Domain.models.User
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

interface UserRepository {

    suspend fun getCompteForUserId(userId: String): Compte?

    suspend fun getFactureNonPayeForUserId(userId: String,idContrat: String,domaine: String) : ArrayList<Facture>

    suspend fun getCurrentUser(userId: String) : User?

    suspend fun getCardsForCurrentUser(userId: String) : ArrayList<Carte>

    suspend fun getComptesForCurrentUser(userId: String) : ArrayList<Compte>

    suspend fun createBankingAccount(userId: String,compteNum: String)





}
