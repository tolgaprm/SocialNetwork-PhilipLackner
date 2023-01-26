package com.prmto.socialnetwork_philiplackner.di

import com.prmto.socialnetwork_philiplackner.feature_activity.data.remote.ActivityApi
import com.prmto.socialnetwork_philiplackner.feature_activity.data.repository.ActivityRepositoryImpl
import com.prmto.socialnetwork_philiplackner.feature_activity.domain.repository.ActivityRepository
import com.prmto.socialnetwork_philiplackner.feature_activity.domain.use_case.GetActivitiesUseCase
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
object ActivityModule {

    @Provides
    @Singleton
    fun provideActivityApi(client: OkHttpClient): ActivityApi {
        return Retrofit.Builder()
            .baseUrl(ActivityApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ActivityApi::class.java)
    }


    @Provides
    @Singleton
    fun provideActivityRepository(api: ActivityApi): ActivityRepository {
        return ActivityRepositoryImpl(api)
    }


    @Provides
    @Singleton
    fun provideGetActivityUseCase(repository: ActivityRepository): GetActivitiesUseCase {
        return GetActivitiesUseCase(repository)
    }
}