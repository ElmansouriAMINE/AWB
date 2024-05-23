package com.example.testoo.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testoo.Domain.models.*
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import kotlin.reflect.KMutableProperty1

class CardsConfigViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance()

    private val _currentCardItem = MutableLiveData<ImageItem?>()
    val currentCardItem : LiveData<ImageItem?> get() = _currentCardItem


    fun setCurrentCardItem(currentCardItem : ImageItem){
        _currentCardItem.value = currentCardItem
    }


    private val _retraitMaroc = MutableLiveData<Int?>()
    val retraitMaroc : LiveData<Int?> get() = _retraitMaroc


    fun setRetraitMaroc(retraitMaroc : Int?){
        _retraitMaroc.value = retraitMaroc
    }

    private val _tpeMaroc = MutableLiveData<Int?>()
    val tpeMaroc : LiveData<Int?> get() = _tpeMaroc


    fun setTpeMaroc(tpeMaroc : Int){
        _tpeMaroc.value = tpeMaroc
    }

    private val _internetMaroc = MutableLiveData<Int?>()
    val internetMaroc : LiveData<Int?> get() = _internetMaroc


    fun setInternetMaroc(internetMaroc : Int){
        _internetMaroc.value = internetMaroc
    }

    fun resetValues() {
        _currentCardItem.value = null
        _retraitMaroc.value = null
        _internetMaroc.value = null
        _tpeMaroc.value = null
    }



//    suspend fun updateCardEtatForIdCard(numCard: String, parameterName: String) {
//        val currentUserCard = FirebaseDatabase.getInstance().getReference("cartes")
//        val query = currentUserCard.orderByChild("numeroCarte").equalTo(numCard)
//        try {
//            val dataSnapshot = query.get().await()
//            val dataSnapshot2 = dataSnapshot.children.mapNotNull { it.getValue(Carte::class.java) }
//
//            if (dataSnapshot2.isNotEmpty()) {
//                val card = dataSnapshot2[0]
//                card.configuration?.let { config ->
//                    when (parameterName) {
//                        "contactless" -> config.contactless = !config.contactless!!
//                        "internetMaroc" -> config.internetMaroc = !config.internetMaroc!!
//                        "tpeMaroc" -> config.tpeMaroc = !config.tpeMaroc!!
//                        "retraitMaroc" -> config.retraitMaroc = !config.retraitMaroc!!
//                        "internetEtranger" -> config.internetEtranger = !config.internetEtranger!!
//                        "tpeEtranger" -> config.tpeEtranger = !config.tpeEtranger!!
//                    }
//
////                    val cardRef = currentUserCard.child(card.id.toString())
////                    cardRef.setValue(card).await()
//                    val cardKey = cardSnapshot.key
//                    cardKey?.let {
//                        currentUserCard.child(it).setValue(card).await()
//                    }
//                }
//            }
//        } catch (e: Exception) {
//            println("Error updating card etat: ${e.message}")
//        }
//    }
suspend fun updateCardEtatForIdCard(numCard: String, parameterName: String) {
    val currentUserCard = FirebaseDatabase.getInstance().getReference("cartes")
    val query = currentUserCard.orderByChild("numeroCarte").equalTo(numCard)
    try {
        val dataSnapshot = query.get().await()
        if (dataSnapshot.exists()) {
            val cardSnapshot = dataSnapshot.children.first()
            val card = cardSnapshot.getValue(Carte::class.java)
            card?.configuration?.let { config ->
                when (parameterName) {
                    "contactless" -> config.contactless = !config.contactless!!
                    "internetMaroc" -> config.internetMaroc = !config.internetMaroc!!
                    "tpeMaroc" -> config.tpeMaroc = !config.tpeMaroc!!
                    "retraitMaroc" -> config.retraitMaroc = !config.retraitMaroc!!
                    "internetEtranger" -> config.internetEtranger = !config.internetEtranger!!
                    "tpeEtranger" -> config.tpeEtranger = !config.tpeEtranger!!
                }

                val cardKey = cardSnapshot.key
                cardKey?.let {
                    currentUserCard.child(it).setValue(card).await()
                }
            }
        }
    } catch (e: Exception) {
        println("Error updating card etat: ${e.message}")
    }
}


    suspend fun getCurrentCard(numeroCarte: String): Carte? {
        val cardsRef = database.getReference("cartes")
        val queryById = cardsRef.orderByChild("numeroCarte").equalTo(numeroCarte)

        return try {
            val dataSnapshotById = queryById.get().await()
            val currentCardItem= dataSnapshotById.children.mapNotNull { it.getValue(
                Carte::class.java) }.firstOrNull()

            currentCardItem
        } catch (e: Exception) {
            println("Error fetching card: ${e.message}")
            null
        }
    }

//    suspend fun updatePlafondValueForIdCard(numCard: String, parameterName: String, montant: Int) {
//        val currentUserCard = FirebaseDatabase.getInstance().getReference("cartes")
//        val query = currentUserCard.orderByChild("numeroCarte").equalTo(numCard)
//        try {
//            val dataSnapshot = query.get().await()
//            if (dataSnapshot.exists()) {
//                val cardSnapshot = dataSnapshot.children.first()
//                val card = cardSnapshot.getValue(Carte::class.java)
//                card?.plafond?.let { config ->
//                    when (parameterName) {
//                        "retraitMaroc" -> config.retraitMaroc = montant.toString()
//                        "internetMaroc" -> config.internetMaroc = montant.toString()
//                        "tpeMaroc" -> config.tpeMaroc = montant.toString()
//
//                    }
//
//                    val cardKey = cardSnapshot.key
//                    cardKey?.let {
//                        currentUserCard.child(it).setValue(card).await()
//                    }
//                }
//            }
//        } catch (e: Exception) {
//            println("Error updating card plafond: ${e.message}")
//        }
//    }

    suspend fun updatePlafondValueForIdCard(numCard: String, parameterName: String, montant: Int) {
        val currentUserCard = FirebaseDatabase.getInstance().getReference("cartes")
        val query = currentUserCard.orderByChild("numeroCarte").equalTo(numCard)
        try {
            val dataSnapshot = query.get().await()
            if (dataSnapshot.exists()) {
                val cardSnapshot = dataSnapshot.children.first()
                val card = cardSnapshot.getValue(Carte::class.java)
                card?.plafond?.let { config ->
                    when (parameterName) {
                        "retraitMaroc" -> config.retraitMaroc = montant
                        "internetMaroc" -> config.internetMaroc = montant
                        "tpeMaroc" -> config.tpeMaroc = montant
                        else -> throw IllegalArgumentException("Invalid parameter name")
                    }

                    val cardKey = cardSnapshot.key
                    if (cardKey != null) {
                        currentUserCard.child(cardKey).setValue(card).await()
                        println("Successfully updated card plafond for $numCard")
                    } else {
                        println("Card key is null")
                    }
                } ?: println("Plafond configuration not found")
            } else {
                println("Card with number $numCard not found")
            }
        } catch (e: Exception) {
            println("Error updating card plafond: ${e.message}")
        }
    }



    suspend fun updateOppositionForIdCard(numCard: String, parameterName: String,commentaire: String) {
        val currentUserCard = FirebaseDatabase.getInstance().getReference("cartes")
        val query = currentUserCard.orderByChild("numeroCarte").equalTo(numCard)
        try {
            val dataSnapshot = query.get().await()
            if (dataSnapshot.exists()) {
                val cardSnapshot = dataSnapshot.children.first()
                val card = cardSnapshot.getValue(Carte::class.java)
                card?.opposition?.let { config ->
                    when (parameterName) {
                        "Vol" -> {
                            config.vol = true
                            config.perte = false
                            config.commentaire=commentaire}
                        "Perte" -> {
                            config.perte = true
                            config.vol = false
                            config.commentaire=commentaire
                        }

                        else -> throw IllegalArgumentException("Invalid parameter name")
                    }

                    val cardKey = cardSnapshot.key
                    if (cardKey != null) {
                        currentUserCard.child(cardKey).setValue(card).await()
                        println("Successfully updated card opposition for $numCard")
                    } else {
                        println("Card key is null")
                    }
                } ?: println("Opposition configuration not found")
            } else {
                println("Card with number $numCard not found")
            }
        } catch (e: Exception) {
            println("Error updating card opposition: ${e.message}")
        }
    }




}
