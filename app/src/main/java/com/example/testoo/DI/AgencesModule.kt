package com.example.testoo.DI

import com.example.testoo.Common.Constants
import com.example.testoo.Data.Repository.WafaCashRepositoryImpl
import com.example.testoo.Data.remote.AgenceWafaCashApi
import com.example.testoo.Domain.Repository.WafaCashRepository
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
    fun provideAgenceWafaCashApi() : AgenceWafaCashApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AgenceWafaCashApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWafaCashRepository(api: AgenceWafaCashApi): WafaCashRepository{
        return WafaCashRepositoryImpl(api)
    }

}
