package com.example.testoo.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testoo.Domain.models.Carte
import com.example.testoo.Domain.models.Configuration
import com.example.testoo.Domain.models.Facture
import com.example.testoo.Domain.models.ImageItem
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import kotlin.reflect.KMutableProperty1

class CardsConfigViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance()

    private val _currentCardItem = MutableLiveData<ImageItem>()
    val currentCardItem : LiveData<ImageItem> get() = _currentCardItem


    fun setCurrentCardItem(currentCardItem : ImageItem){
        _currentCardItem.value = currentCardItem
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




}
