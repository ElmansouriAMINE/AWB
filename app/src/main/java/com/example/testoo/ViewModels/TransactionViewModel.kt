package com.example.testoo.ViewModels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testoo.Domain.models.Transaction
import com.example.testoo.Domain.models.User
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

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
//    suspend fun getAllTransactions(userId: String, user: User): ArrayList<Transaction> {
//        val transactionsRef = database.getReference("transactions")
//        val queryById = transactionsRef.orderByChild("userId").equalTo(userId)
//        val queryByReceiverName = transactionsRef.orderByChild("receiverName").equalTo(user.userName)
//
//        return try {
//            val dataSnapshotById = queryById.get().await()
//            val dataSnapshotByReceiverName = queryByReceiverName.get().await()
//
//            val transactionsListById = dataSnapshotById.children.mapNotNull { it.getValue(Transaction::class.java) }
//            val transactionsListByReceiverName = dataSnapshotByReceiverName.children.mapNotNull { it.getValue(Transaction::class.java) }
//
//            // Combine both lists
//            val combinedList = (transactionsListById + transactionsListByReceiverName)
//                .sortedByDescending { it.dateHeure }
//            ArrayList(combinedList)
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
            val combinedList = (transactionsListById + transactionsListByReceiverName)
                .sortedByDescending { parseDateTime(it.dateHeure) }
            ArrayList(combinedList)
        } catch (e: Exception) {
            println("Error fetching transactions: ${e.message}")
            ArrayList()
        }
    }


    private fun parseDateTime(dateTimeString: String?): LocalDateTime? {
        return try {
            dateTimeString?.let {
                val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
                LocalDateTime.parse(it, formatter)
            }
        } catch (e: Exception) {
            println("Error parsing date time: ${e.message}")
            null
        }
    }



    suspend fun getAllCardTransactions(userId: String): ArrayList<Transaction> {
        val transactionsRef = database.getReference("transactions")
        val queryById = transactionsRef.orderByChild("userId").equalTo(userId)

        return try {
            val dataSnapshotById = queryById.get().await()
            val transactionsListById = dataSnapshotById.children.mapNotNull { it.getValue(Transaction::class.java) }
            val transactionsListByDomaineName = transactionsListById.filter { it.domaine == "Card" }

            ArrayList(transactionsListByDomaineName.sortedByDescending { parseDateTime(it.dateHeure) })
        } catch (e: Exception) {
            println("Error fetching transactions: ${e.message}")
            ArrayList()
        }
    }


    suspend fun getRecentThreeTransactions(userId: String, user: User): ArrayList<Transaction> {
        val transactionsRef = database.getReference("transactions")
        val queryById = transactionsRef.orderByChild("userId").equalTo(userId)
        val queryByReceiverName = transactionsRef.orderByChild("receiverName").equalTo(user.userName)

        return try {
            val dataSnapshotById = queryById.get().await()
            val dataSnapshotByReceiverName = queryByReceiverName.get().await()

            val transactionsListById = dataSnapshotById.children.mapNotNull { it.getValue(Transaction::class.java) }.distinct()
            val transactionsListByReceiverName = dataSnapshotByReceiverName.children.mapNotNull { it.getValue(Transaction::class.java) }

//            // Combine both lists and sort by dateHeure
            val combinedList = (transactionsListById + transactionsListByReceiverName)
                .sortedByDescending { parseDateTime(it.dateHeure) }


            ArrayList(combinedList.take(3))
        } catch (e: Exception) {
            println("Error fetching transactions: ${e.message}")
            ArrayList()
        }
    }





}
