package com.example.testoo.Domain.models

data class AccountItem(
    val id:String? = null,
    var solde:Double? = null,
    val numero:String? = null,
    val userId:String?=null,
    val dateOuverture:String? = null,
    val imageResId: Int?=null,
    val transactions:List<Transaction>? = null

)
