package com.example.testoo.models

data class Compte(
    val id:Int? = null,
    val userId:String?=null,
    val numero:String? = null,
    val solde:Double? = null,
    val dateOuverture:String? = null,
    val transactions:List<Transaction>? = null
)
