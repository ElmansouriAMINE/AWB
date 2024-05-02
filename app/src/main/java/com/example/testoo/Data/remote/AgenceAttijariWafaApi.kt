package com.example.testoo.Data.remote

import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Query

interface AgenceAttijariWafaApi {

    @GET("/geoserver/geoloc_getPOI.php?")
    suspend fun getAttijariWafaNear(
        @Query("latitude") latitude: Double,
        @Query("action") action: String,
        @Query("longitude") longitude: Double
    ): JsonObject
}
