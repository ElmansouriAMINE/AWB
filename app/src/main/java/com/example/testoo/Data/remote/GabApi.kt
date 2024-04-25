package com.example.testoo.Data.remote

import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Query

interface GabApi {

    @GET("/geoserver/geoloc_getPOI.php?")
    suspend fun getGabsNear(
        @Query("latitude") latitude: Double,
        @Query("action") action: String,
        @Query("longitude") longitude: Double
    ): JsonObject

}
