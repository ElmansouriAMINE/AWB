package com.example.testoo.Domain.Repository

import com.example.testoo.Data.remote.Dto.AgenceAttijariWafaDto
import com.example.testoo.Data.remote.Dto.AgenceWafaCashDto

interface AttijariWafaRepository {

    suspend fun getAttijariWafaNear() : List<AgenceAttijariWafaDto>
}
