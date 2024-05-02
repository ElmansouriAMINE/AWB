package com.example.testoo.DI

import com.example.testoo.Common.Constants
import com.example.testoo.Data.Repository.AttijariWafaRepositoryImpl
import com.example.testoo.Data.Repository.GabRepositoryImpl
import com.example.testoo.Data.remote.AgenceAttijariWafaApi
import com.example.testoo.Data.remote.GabApi
import com.example.testoo.Domain.Repository.AttijariWafaRepository
import com.example.testoo.Domain.Repository.GabRepository
import com.example.testoo.UI.UseCase.GetAgencesAttijariWafaNearUseCase
import com.example.testoo.UI.UseCase.GetGabsNearUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AttijariWafaModule {

    @Provides
    @Singleton
    fun provideAttijariWafaApi(): AgenceAttijariWafaApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(AgenceAttijariWafaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAttijariwafaRepository(api: AgenceAttijariWafaApi, gson: Gson): AttijariWafaRepository {
        return AttijariWafaRepositoryImpl(api,gson)
    }

    //    @Provides
//    @Singleton
//    fun provideGetAgencesWafaCashNearUseCase(repository: WafaCashRepository): GetAgencesWafaCashNearUseCase {
//        return GetAgencesWafaCashNearUseCase(repository)
//    }
    @Provides
    @Singleton
    fun provideGetAgencesAttijariWafaNearUseCase(repository: AttijariWafaRepository): GetAgencesAttijariWafaNearUseCase {
        return GetAgencesAttijariWafaNearUseCase(repository)
    }

//    @Provides
//    @Singleton
//    fun provideGson(): Gson {
//        return Gson()
//    }





}
