package com.example.testoo.Domain.models

data class Contrat(val reference: String?=null,val userId : String?=null,val domaine: String?=null, val factures : List<Facture> ?=null)

