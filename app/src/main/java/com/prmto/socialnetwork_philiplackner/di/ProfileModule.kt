package com.prmto.socialnetwork_philiplackner.di

import com.google.gson.Gson
import com.prmto.socialnetwork_philiplackner.feature_profile.data.remote.ProfileApi
import com.prmto.socialnetwork_philiplackner.feature_profile.data.repository.ProfileRepositoryImpl
import com.prmto.socialnetwork_philiplackner.feature_profile.domain.repository.ProfileRepository
import com.prmto.socialnetwork_philiplackner.feature_profile.domain.use_case.*
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun provideProfileApi(client: OkHttpClient): ProfileApi {
        return Retrofit.Builder()
            .baseUrl(ProfileApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(api: ProfileApi, gson: Gson): ProfileRepository {
        return ProfileRepositoryImpl(api, gson)
    }

    @Provides
    @Singleton
    fun provideProfileUseCases(repository: ProfileRepository): ProfileUseCases {
        return ProfileUseCases(
            GetProfileUseCase(repository),
            GetSkillsUseCase(repository),
            UpdateProfileUseCase(repository),
            SetSkillsSelectedUseCase()
        )
    }
}