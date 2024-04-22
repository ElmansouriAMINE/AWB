package com.example.testoo.Data.Repository

import com.example.testoo.Data.remote.AgenceWafaCashApi
import com.example.testoo.Data.remote.Dto.AgenceWafaCashDto
import com.example.testoo.Domain.Repository.WafaCashRepository
import javax.inject.Inject

class WafaCashRepositoryImpl @Inject constructor(
    private val api: AgenceWafaCashApi
): WafaCashRepository {
    override suspend fun getAgencesWafacashNear(): List<AgenceWafaCashDto> {
        return api.getAgencesWafacashNear()
    }

}
