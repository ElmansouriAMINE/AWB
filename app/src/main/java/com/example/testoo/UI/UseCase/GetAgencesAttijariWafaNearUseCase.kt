package com.example.testoo.UI.UseCase

import android.net.http.HttpException
import androidx.annotation.RequiresApi
import com.example.testoo.Common.Resource
import com.example.testoo.Data.remote.Dto.AgenceAttijariWafaDto
import com.example.testoo.Data.remote.Dto.AgenceWafaCashDto
import com.example.testoo.Domain.Repository.AttijariWafaRepository
import com.example.testoo.Domain.Repository.WafaCashRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject


class GetAgencesAttijariWafaNearUseCase @Inject constructor(
    private val repository: AttijariWafaRepository
) {

    @RequiresApi(34)
    operator fun invoke(): Flow<Resource<List<AgenceAttijariWafaDto>>> = flow {
        try {

            println("OOOOOOOOO")

            println("Agences :  ${repository.getAttijariWafaNear()}")
            emit(Resource.Loading())
            val agencesAttijariWafa = repository.getAttijariWafaNear()

            println("Agences : $agencesAttijariWafa")

            emit(Resource.Success(agencesAttijariWafa))
            println("pppp"+ Resource.Success(agencesAttijariWafa))

        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error has been occured"))

        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server.Plz check your connection"))

        }
    }
}
