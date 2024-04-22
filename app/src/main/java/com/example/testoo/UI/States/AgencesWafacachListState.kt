package com.example.testoo.UI.States

import com.example.testoo.Data.remote.Dto.AgenceWafaCashDto

data class AgencesWafacachListState(
    val isLoading:Boolean =false,
    val agencesWafaCashList: List<AgenceWafaCashDto> = emptyList(),
    val error: String = ""
)
