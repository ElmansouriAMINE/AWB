package com.example.testoo.Data.Repository

import com.example.testoo.Domain.Repository.UserRepository
import com.example.testoo.Domain.models.*
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

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

    override suspend fun createBankingAccount(userId: String,compteNum: String){
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        val compteInitial = Compte(userId=userId,numero = "$compteNum", solde = 0.0, dateOuverture = currentDate)
        val comptesCollection = FirebaseDatabase.getInstance().getReference("comptes")
        val comptekey = comptesCollection.push().key
        comptekey?.let{
            comptesCollection.child(it).setValue(compteInitial)
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

    override suspend fun getCardsForCurrentUser(userId: String) : ArrayList<Carte>{
        val cardsCollection = FirebaseDatabase.getInstance().getReference("cartes")
        val query= cardsCollection.orderByChild("userId").equalTo(userId)

        return try{
            val datasnapshot = query.get().await()
            val carte = datasnapshot.children.mapNotNull { it.getValue(Carte::class.java) }
            ArrayList(carte)

        }catch (e:Exception){
            println("Error fetching cards : ${e.message}")
            ArrayList()
        }
    }

    override suspend fun getComptesForCurrentUser(userId: String) : ArrayList<Compte>{
        val cardsCollection = FirebaseDatabase.getInstance().getReference("comptes")
        val query= cardsCollection.orderByChild("userId").equalTo(userId)

        return try{
            val datasnapshot = query.get().await()
            val carte = datasnapshot.children.mapNotNull { it.getValue(Compte::class.java) }
            ArrayList(carte)

        }catch (e:Exception){
            println("Error fetching banking accounts : ${e.message}")
            ArrayList()
        }
    }

}
