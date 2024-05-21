package com.example.testoo.Domain.models

data class ImageItem(
    val id : String,
    val imageUrl : String,
    val dateExpiration: String,
    val numeroCarte : String,
    val userName : String,
    var configuration: Configuration?=null

)
