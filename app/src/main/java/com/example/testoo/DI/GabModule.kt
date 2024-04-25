package com.example.testoo.DI

import com.example.testoo.Common.Constants
import com.example.testoo.Data.Repository.GabRepositoryImpl
import com.example.testoo.Data.Repository.WafaCashRepositoryImpl
import com.example.testoo.Data.remote.AgenceWafaCashApi
import com.example.testoo.Data.remote.GabApi
import com.example.testoo.Domain.Repository.GabRepository
import com.example.testoo.Domain.Repository.WafaCashRepository
import com.example.testoo.UI.UseCase.GetAgencesWafaCashNearUseCase
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
object GabModule {

    @Provides
    @Singleton
    fun provideGabApi(): GabApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(GabApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGabRepository(api: GabApi, gson: Gson): GabRepository {
        return GabRepositoryImpl(api,gson)
    }

    //    @Provides
//    @Singleton
//    fun provideGetAgencesWafaCashNearUseCase(repository: WafaCashRepository): GetAgencesWafaCashNearUseCase {
//        return GetAgencesWafaCashNearUseCase(repository)
//    }
    @Provides
    @Singleton
    fun provideGetGabNearUseCase(repository: GabRepository): GetGabsNearUseCase {
        return GetGabsNearUseCase(repository)
    }

//    @Provides
//    @Singleton
//    fun provideGson(): Gson {
//        return Gson()
//    }





}
