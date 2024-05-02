package com.example.testoo.Data.Repository

import com.example.testoo.Data.remote.AgenceAttijariWafaApi
import com.example.testoo.Data.remote.Dto.AgenceAttijariWafaDto
import com.example.testoo.Data.remote.Dto.GabDto
import com.example.testoo.Domain.Repository.AttijariWafaRepository
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class AttijariWafaRepositoryImpl @Inject constructor(
    private val api: AgenceAttijariWafaApi,
    private val gson: Gson

):AttijariWafaRepository
{
    override suspend fun getAttijariWafaNear(): List<AgenceAttijariWafaDto> {
        println("Geting gabs...")
        val responseString = api.getAttijariWafaNear(33.5850405, "getAgencesNear", -7.6219494)
        val responseJsonObject = gson.fromJson(responseString, JsonObject::class.java)
        val attijariWafaJsonArray = responseJsonObject.getAsJsonArray("agence")
        return gson.fromJson(attijariWafaJsonArray, object : TypeToken<List<AgenceAttijariWafaDto>>() {}.type)
    }

}
