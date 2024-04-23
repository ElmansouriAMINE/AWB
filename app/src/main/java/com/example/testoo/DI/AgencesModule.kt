package com.example.testoo.DI

import androidx.lifecycle.ViewModelProvider
import com.example.testoo.Common.Constants
import com.example.testoo.Data.Repository.WafaCashRepositoryImpl
import com.example.testoo.Data.remote.AgenceWafaCashApi
import com.example.testoo.Domain.Repository.WafaCashRepository
import com.example.testoo.UI.UseCase.GetAgencesWafaCashNearUseCase
import com.example.testoo.ViewModels.WafaCashViewModel
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AgencesModule {

    @Provides
    @Singleton
    fun provideAgenceWafaCashApi(): AgenceWafaCashApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(AgenceWafaCashApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWafaCashRepository(api: AgenceWafaCashApi,gson: Gson): WafaCashRepository{
        return WafaCashRepositoryImpl(api,gson)
    }

//    @Provides
//    @Singleton
//    fun provideGetAgencesWafaCashNearUseCase(repository: WafaCashRepository): GetAgencesWafaCashNearUseCase {
//        return GetAgencesWafaCashNearUseCase(repository)
//    }
    @Provides
    @Singleton
    fun provideGetAgencesWafaCashNearUseCase(repository: WafaCashRepository): GetAgencesWafaCashNearUseCase {
        return GetAgencesWafaCashNearUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }





}
