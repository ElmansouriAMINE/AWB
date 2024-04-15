package com.example.testoo.models

data class Transaction (
    val imgProfile: String? =null,
    val userId:String? =null,
    val senderName: String?=null,
    val receiverName: String?=null,
    val montant:String?=null,
    val dateHeure : String?=null,
)
