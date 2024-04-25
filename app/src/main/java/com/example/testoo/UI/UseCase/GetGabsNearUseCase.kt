package com.example.testoo.UI.UseCase

import android.net.http.HttpException
import androidx.annotation.RequiresApi
import com.example.testoo.Common.Resource
import com.example.testoo.Data.remote.Dto.AgenceWafaCashDto
import com.example.testoo.Data.remote.Dto.GabDto
import com.example.testoo.Domain.Repository.GabRepository
import com.example.testoo.Domain.Repository.WafaCashRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetGabsNearUseCase @Inject constructor(
    private val repository: GabRepository
) {

    @RequiresApi(34)
    operator fun invoke(): Flow<Resource<List<GabDto>>> = flow {
        try {

            println("OOOOOOOOO")

            println("Gabs :  ${repository.getGabsNear()}")
            emit(Resource.Loading())
            val gabs = repository.getGabsNear()

            println("Gabs : $gabs")

            emit(Resource.Success(gabs))
            println("success"+ Resource.Success(gabs))

        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected erro has been occured"))

        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server.Plz check your connection"))

        }
    }
}
