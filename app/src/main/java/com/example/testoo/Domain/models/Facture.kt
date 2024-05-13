package com.example.testoo.Domain.models

data class Facture(val nomFacture:String?= null, val montant:String?=null,
                   var etat:Boolean=false, val userId:String?=null, val idContrat : String?=null)

