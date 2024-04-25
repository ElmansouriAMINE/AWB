package com.example.testoo.Data.Repository

import com.example.testoo.Data.remote.AgenceWafaCashApi
import com.example.testoo.Data.remote.Dto.AgenceWafaCashDto
import com.example.testoo.Data.remote.Dto.GabDto
import com.example.testoo.Data.remote.GabApi
import com.example.testoo.Domain.Repository.GabRepository
import com.example.testoo.Domain.Repository.WafaCashRepository
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import javax.inject.Inject


class GabRepositoryImpl @Inject constructor(
    private val api: GabApi,
    private val gson: Gson
): GabRepository
{
    override suspend fun getGabsNear(): List<GabDto> {
        println("Geting gabs...")
        val responseString = api.getGabsNear(33.5850405, "getGABNear", -7.6219494)
        val responseJsonObject = gson.fromJson(responseString, JsonObject::class.java)
        val gabsJsonArray = responseJsonObject.getAsJsonArray("gab")
        return gson.fromJson(gabsJsonArray, object : TypeToken<List<GabDto>>() {}.type)
    }
}

