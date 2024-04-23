package com.example.testoo.UI.UseCase

import android.net.http.HttpException
import androidx.annotation.RequiresApi
import com.example.testoo.Common.Resource
import com.example.testoo.Data.remote.Dto.AgenceWafaCashDto
import com.example.testoo.Domain.Repository.WafaCashRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetAgencesWafaCashNearUseCase @Inject constructor(
    private val repository: WafaCashRepository
) {

    @RequiresApi(34)
    operator fun invoke(): Flow<Resource<List<AgenceWafaCashDto>>> = flow {
            try {

                println("OOOOOOOOO")

                println("Agences :  ${repository.getAgencesWafacashNear()}")
                emit(Resource.Loading())
                val agencesWafaCash = repository.getAgencesWafacashNear()

                println("Agences : $agencesWafaCash")

                emit(Resource.Success(agencesWafaCash))
                println("pppp"+Resource.Success(agencesWafaCash))

            }catch (e: HttpException){
                emit(Resource.Error(e.localizedMessage ?: "An unexpected erro has been occured"))

            }catch (e: IOException){
                emit(Resource.Error("Couldn't reach server.Plz check your connection"))

            }
    }
}

