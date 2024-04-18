package com.example.testoo.ViewModels

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testoo.R
import com.example.testoo.SignInFragment
import com.example.testoo.models.Carte
import com.example.testoo.models.Compte
import com.example.testoo.models.Transaction
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

class VirementViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance()
    private val _data =MutableLiveData<String>()
    val data: LiveData<String> get() = _data

    private val _beneficiaireId =MutableLiveData<String>()
    val beneficiaireId: LiveData<String> get() = _beneficiaireId

    private val _data2 =MutableLiveData<String>()
    val data2: LiveData<String> get() = _data2


    private val _beneficiaire =MutableLiveData<String>()
    val beneficiaire: LiveData<String> get() = _beneficiaire

    private val _montant =MutableLiveData<String>()
    val montant: LiveData<String> get() = _montant

    private val _motif =MutableLiveData<String>()
    val motif: LiveData<String> get() = _motif


    suspend fun getComptesForUserId(userId: String): List<Compte> {
        val currentUserCompte = database.getReference("comptes")
        val query = currentUserCompte.orderByChild("userId").equalTo(userId)
        return try {
            val dataSnapshot = query.get().await()
            dataSnapshot.children.mapNotNull { it.getValue(Compte::class.java) }
        } catch (e: Exception) {
            println("Error fetching comptes: ${e.message}")
            emptyList()
        }
    }

    fun createTransaction(currentTransaction: Transaction){
        val transactionCollection= database.getReference("transactions")
//        val currentTransaction= Transaction("","","","","")
        val transactionkey = transactionCollection.push().key
        transactionkey?.let {
            transactionCollection.child(it).setValue(currentTransaction)
        }

    }

    fun getCurrentDateTimeFormatted(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    suspend fun updateCompteSoldeForUserId(userId: String, newSolde: Int) {
        val currentUserCompte = database.getReference("comptes")
        val query = currentUserCompte.orderByChild("userId").equalTo(userId)
        try {
            val dataSnapshot = query.get().await()
            dataSnapshot.children.forEach { snapshot ->
                val compte = snapshot.getValue(Compte::class.java)
                compte?.let {
                    it.solde = newSolde.toDouble()
                    snapshot.ref.setValue(it)
                }
            }
        } catch (e: Exception) {
            println("Error updating compte solde: ${e.message}")
        }
    }

    internal fun generateOTP(length: Int): String {
        return (0 until length)
            .map { (0..9).random() }
            .joinToString("")
    }











    fun setData(data:String){
        _data.value= data
    }

    fun setMontant(data:String){
        _montant.value= data
    }
    fun setMotif(data:String){
        _motif.value= data
    }
    fun setBeneficiaire(data:String){
        _beneficiaire.value= data
    }

    fun setBeneficiaireId(data: String){
        _beneficiaireId.value= data
    }


    fun setData2(data2:String){
        _data2.value =data2

    }
}
