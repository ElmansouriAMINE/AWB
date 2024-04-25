package com.example.testoo.Domain.Repository

import com.example.testoo.Data.remote.Dto.GabDto

interface GabRepository {

    suspend fun getGabsNear() : List<GabDto>
}

