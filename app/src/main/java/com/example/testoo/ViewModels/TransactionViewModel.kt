package com.example.testoo.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testoo.Domain.models.Transaction
import com.example.testoo.Domain.models.User
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class TransactionViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance()

//    suspend fun getAllTransactions(userId: String,user: User): ArrayList<Transaction> {
//        val transactionsRef = database.getReference("transactions")
//        val query = transactionsRef.orderByChild("userId").equalTo(userId)
//
//        return try {
//            val dataSnapshot = query.get().await()
//            val transactionsList = dataSnapshot.children.mapNotNull { it.getValue(Transaction::class.java)}
//            ArrayList(transactionsList)
//        } catch (e: Exception) {
//            println("Error fetching transactions: ${e.message}")
//            ArrayList()
//        }
//    }
    suspend fun getAllTransactions(userId: String, user: User): ArrayList<Transaction> {
        val transactionsRef = database.getReference("transactions")
        val queryById = transactionsRef.orderByChild("userId").equalTo(userId)
        val queryByReceiverName = transactionsRef.orderByChild("receiverName").equalTo(user.userName)

        return try {
            val dataSnapshotById = queryById.get().await()
            val dataSnapshotByReceiverName = queryByReceiverName.get().await()

            val transactionsListById = dataSnapshotById.children.mapNotNull { it.getValue(Transaction::class.java) }
            val transactionsListByReceiverName = dataSnapshotByReceiverName.children.mapNotNull { it.getValue(Transaction::class.java) }

            // Combine both lists
            val combinedList = transactionsListById + transactionsListByReceiverName
            ArrayList(combinedList)
        } catch (e: Exception) {
            println("Error fetching transactions: ${e.message}")
            ArrayList()
        }
    }




}
