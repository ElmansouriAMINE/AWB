package com.example.testoo.Domain.models

data class Configuration(
    var contactless:Boolean? =false,
    var internetMaroc:Boolean?=false,
    var tpeMaroc:Boolean?=false,
    var retraitMaroc:Boolean?=false,
    var internetEtranger:Boolean?=false,
    var tpeEtranger: Boolean ?=false
)
