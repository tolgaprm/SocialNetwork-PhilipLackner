package com.prmto.socialnetwork_philiplackner.di

import com.prmto.socialnetwork_philiplackner.feature_auth.data.remote.AuthApi
import com.prmto.socialnetwork_philiplackner.feature_auth.data.repository.AuthRepositoryImpl
import com.prmto.socialnetwork_philiplackner.feature_auth.domain.repository.AuthRepository
import com.prmto.socialnetwork_philiplackner.feature_auth.domain.use_case.RegisterUseCase
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder()
            .baseUrl(AuthApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }


    @Provides
    @Singleton
    fun provideAuthRepository(authApi: AuthApi): AuthRepository {
        return AuthRepositoryImpl(authApi)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(repository: AuthRepository): RegisterUseCase {
        return RegisterUseCase(repository = repository)
    }

}