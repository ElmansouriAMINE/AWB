package com.example.testoo.UI.States

import com.example.testoo.Data.remote.Dto.AgenceAttijariWafaDto
import com.example.testoo.Data.remote.Dto.AgenceWafaCashDto

data class AgencesAttijariWafaListState(
    val isLoading:Boolean =false,
    val agencesAttijariWafaList: List<AgenceAttijariWafaDto> = emptyList(),
    val error: String = ""
)
