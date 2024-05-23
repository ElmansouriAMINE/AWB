package com.example.testoo.Domain.models

data class Carte (
    val id:Int? = null,
    val userId:String?=null,
    val numeroCarte:String? =null,
    val codeSecret:String? = null,
    val dateExpiration:String? =null,
    val configuration: Configuration? = null,
    val plafond: Plafond?=null,
    val opposition: Opposition?=null
)
