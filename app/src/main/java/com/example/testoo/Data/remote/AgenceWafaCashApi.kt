package com.example.testoo.Data.remote

import com.example.testoo.Data.remote.Dto.AgenceWafaCashDto
import retrofit2.http.GET

interface AgenceWafaCashApi {

    @GET("/geoloc_getPOI.php?latitude=33.5850405&action=getAgencesWafacashNear&longitude=-7.6219494")
    suspend fun getAgencesWafacashNear() : List<AgenceWafaCashDto>


}
