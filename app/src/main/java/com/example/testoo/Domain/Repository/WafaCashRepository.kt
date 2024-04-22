package com.example.testoo.Domain.Repository

import com.example.testoo.Data.remote.Dto.AgenceWafaCashDto

interface WafaCashRepository {
    suspend fun getAgencesWafacashNear() : List<AgenceWafaCashDto>
}
