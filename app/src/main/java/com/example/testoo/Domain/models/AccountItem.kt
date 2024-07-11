package com.example.testoo.Domain.models

data class AccountItem(
    val id : String,
    val imageResId: Int,
    val dateExpiration: String,
    val numeroCarte : String,
    val userName : String,
    var configuration: Configuration?=null,
    var plafond: Plafond?=null,
    var opposition: Opposition?=null,
    var isChecked: Boolean = false

)
