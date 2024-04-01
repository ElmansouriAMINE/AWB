package com.example.testoo.models

data class User(
    val userName: String? =null,
    val email: String? =null,
    val phoneNumber: String? =null,
    val photoUrl:String? =null,
    val comptes: List<Compte>? = null,
    val cartes:List<Carte>? = null

)

