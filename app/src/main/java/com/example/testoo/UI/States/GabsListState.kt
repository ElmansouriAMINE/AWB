package com.example.testoo.UI.States

import com.example.testoo.Data.remote.Dto.AgenceWafaCashDto
import com.example.testoo.Data.remote.Dto.GabDto

data class GabsListState(
    val isLoading:Boolean =false,
    val gabsList: List<GabDto> = emptyList(),
    val error: String = ""
)

