package com.example.testoo.UI.Adapters

import com.example.testoo.Domain.models.Facture

interface QuantityListener {
    fun onQuantityChange(factures: ArrayList<Facture>)
}
