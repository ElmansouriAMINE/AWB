package com.example.testoo.Data.remote

import com.example.testoo.Data.remote.Dto.AgenceWafaCashDto
import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Query

interface AgenceWafaCashApi {

    @GET("/geoserver/geoloc_getPOI.php?")
    suspend fun getAgencesWafacashNear(
        @Query("latitude") latitude: Double,
        @Query("action") action: String,
        @Query("longitude") longitude: Double
    ): JsonObject

}
