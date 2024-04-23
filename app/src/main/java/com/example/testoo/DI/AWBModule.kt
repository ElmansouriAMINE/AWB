package com.example.testoo.DI

//import com.example.testoo.Data.Repository.UserRepositoryImpl
//import com.example.testoo.Domain.Repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


import com.example.testoo.Data.Repository.UserRepositoryImpl
import com.example.testoo.Domain.Repository.UserRepository


@Module
@InstallIn(SingletonComponent::class)
object AWBModule {

     @Provides
     @Singleton
     fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository {
          return userRepositoryImpl
        }


}

