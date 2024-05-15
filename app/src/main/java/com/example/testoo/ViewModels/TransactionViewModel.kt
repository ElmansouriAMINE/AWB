package com.example.testoo.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testoo.Domain.models.Transaction
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class TransactionViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance()

    suspend fun getAllTransactions(userId: String): ArrayList<Transaction> {
        val transactionsRef = database.getReference("transactions")
        val query = transactionsRef.orderByChild("userId").equalTo(userId)

        return try {
            val dataSnapshot = query.get().await()
            val transactionsList = dataSnapshot.children.mapNotNull { it.getValue(Transaction::class.java) }
            ArrayList(transactionsList)
        } catch (e: Exception) {
            println("Error fetching transactions: ${e.message}")
            ArrayList()
        }
    }


}
