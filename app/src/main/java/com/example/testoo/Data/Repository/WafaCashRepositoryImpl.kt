package com.example.testoo.Data.Repository

import com.example.testoo.Data.remote.AgenceWafaCashApi
import com.example.testoo.Data.remote.Dto.AgenceWafaCashDto
import com.example.testoo.Domain.Repository.WafaCashRepository
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

//class WafaCashRepositoryImpl @Inject constructor(
//    private val api: AgenceWafaCashApi
//): WafaCashRepository {
//    override suspend fun getAgencesWafacashNear(): String {
//        println("iiiii")
//        val agences = api.getAgencesWafacashNear(33.5850405, "getAgencesWafacashNear", -7.6219494)
//        val gson = Gson()
//        return gson.toJson(agences)
////        return api.getAgencesWafacashNear()
//
//    }
//
//}

class WafaCashRepositoryImpl @Inject constructor(
    private val api: AgenceWafaCashApi,
    private val gson: Gson
): WafaCashRepository {
    override suspend fun getAgencesWafacashNear(): List<AgenceWafaCashDto> {
        println("iiiii")
        val responseString = api.getAgencesWafacashNear(33.5850405, "getAgencesWafacashNear", -7.6219494)
        val responseJsonObject = gson.fromJson(responseString, JsonObject::class.java)
        val agencesJsonArray = responseJsonObject.getAsJsonArray("agence")
        return gson.fromJson(agencesJsonArray, object : TypeToken<List<AgenceWafaCashDto>>() {}.type)
//        return response.agence // Assuming 'agence' is the name of the array in your JSON response
    }
}

