package com.example.testoo.Data.Repository

import com.example.testoo.Domain.Repository.UserRepository
import com.example.testoo.Domain.models.Compte
import com.example.testoo.Domain.models.Contrat
import com.example.testoo.Domain.models.Facture
import com.example.testoo.Domain.models.User
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
//    override suspend fun getFactureNonPayeForUserId(userId: String,idContrat: String,domaine:String): List<Facture> {
//        val currentUserFactures = FirebaseDatabase.getInstance().getReference("factures")
//        val currentUserContrat = FirebaseDatabase.getInstance().getReference("contrats")
//        val query = currentUserFactures.orderByChild("userId").equalTo(userId)
//        val query2 = currentUserContrat.orderByChild("userId").equalTo(userId).equalTo(domaine)
//        return try {
//            val dataSnapshot = query.get().await()
//            dataSnapshot.children.mapNotNull { it.getValue(Facture::class.java) }
//                .filter { it.etat == false && it.idContrat =="$idContrat" }
//                .also { factures ->
//                    println("Factures with etat false for userId $userId: $factures")
//                }
//        } catch (e: Exception) {
//            println("Error fetching factures: ${e.message}")
//            emptyList()
//        }
//    }
    override suspend fun getFactureNonPayeForUserId(userId: String, idContrat: String, domaine: String): ArrayList<Facture> {
        val currentUserFactures = FirebaseDatabase.getInstance().getReference("factures")
        val currentUserContrat = FirebaseDatabase.getInstance().getReference("contrats")
        val query = currentUserFactures.orderByChild("userId").equalTo(userId)
        val query2 = currentUserContrat.orderByChild("userId").equalTo(userId)

        return try {
            val dataSnapshot = query.get().await()
            val dataSnapshot2 = query2.get().await()

            val contrats = dataSnapshot2.children.mapNotNull { it.getValue(Contrat::class.java) }

            val contratIdFiltered = contrats.filter { it.domaine == domaine && it.reference == idContrat }.map { it.reference }

            val factures = dataSnapshot.children.mapNotNull { it.getValue(Facture::class.java) }

            val filteredFactures: MutableList<Facture> = factures.filter { it.etat == false && it.idContrat in contratIdFiltered }.toMutableList()

            println("Factures with etat false for userId $userId and idContrat $idContrat: $filteredFactures")

            ArrayList(filteredFactures)
        } catch (e: Exception) {
            println("Error fetching factures: ${e.message}")
            ArrayList()
        }
    }

    override suspend fun getCurrentUser(userId: String): User? {
        val currentUser = FirebaseDatabase.getInstance().getReference("users")
        val query = currentUser.orderByChild("id").equalTo(userId)

        return try {
            val dataSnapshot = query.get().await()
            val user = dataSnapshot.children.mapNotNull { it.getValue(User::class.java) }.firstOrNull()
            user
        } catch (e: Exception) {
            println("Error fetching user: ${e.message}")
            null
        }
    }

}
