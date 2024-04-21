package com.example.testoo.Domain.models

data class User(
    val id:String? =null,
    val userName: String? =null,
    val email: String? =null,
    val phoneNumber: String? =null,
    val photoUrl:String? =null,
    val comptes: List<Compte>? = null,
    val cartes:List<Carte>? = null

)

