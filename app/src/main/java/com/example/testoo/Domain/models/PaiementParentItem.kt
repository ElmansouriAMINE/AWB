package com.example.testoo.Domain.models

data class PaiementParentItem(
    val title: String,
    val image: String? =null,
    val childItemList : ArrayList<PaiementChildItem>,
    var isExpandable : Boolean = false
)
