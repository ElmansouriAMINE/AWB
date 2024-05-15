package com.example.testoo.ViewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.testoo.Domain.Repository.UserRepository
//import com.example.testoo.Data.Repository.UserRepositoryImpl
//import com.example.testoo.Domain.models.Compte
//import dagger.hilt.android.lifecycle.HiltViewModel
//import javax.inject.Inject
//
//
////import androidx.hilt.lifecycle.ViewModelInject
//import androidx.lifecycle.ViewModel
////import com.example.testoo.Data.Repository.UserRepositoryImpl
//import com.example.testoo.Domain.Repository.UserRepository
import com.example.testoo.Domain.models.Compte
import com.example.testoo.Domain.models.Facture
import com.example.testoo.Domain.models.User
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
//import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


//class UserViewModel : ViewModel() {
//
//    suspend fun getCompteForUserId(userId: String): Compte? {
//        val currentUserCompte = FirebaseDatabase.getInstance().getReference("comptes")
//        val query = currentUserCompte.orderByChild("userId").equalTo(userId)
//        return try {
//            val dataSnapshot = query.get().await()
//            val compte = dataSnapshot.children.mapNotNull { it.getValue(Compte::class.java) }.firstOrNull()
//            println("Compte: $compte")
//            compte
//        } catch (e: Exception) {
//            println("Error fetching compte: ${e.message}")
//            null
//        }
//    }
//
//
//}
@HiltViewModel
class UserViewModel  @Inject constructor(
    private val userRepository: UserRepository
    )
    : ViewModel() {
    suspend fun getCompteForUserId(userId: String): Compte? {
        return userRepository.getCompteForUserId(userId)
    }

    suspend fun getFactureNonPayeForUserId(userId: String , idContrat: String , domaine :String): ArrayList<Facture> {
        return userRepository.getFactureNonPayeForUserId(userId,idContrat,domaine)
    }
    suspend fun getCurrentUser(userId: String) : User? {
        return userRepository.getCurrentUser(userId)
    }

}






