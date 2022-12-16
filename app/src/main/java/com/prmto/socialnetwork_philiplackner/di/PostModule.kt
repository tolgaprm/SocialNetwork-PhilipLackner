package com.prmto.socialnetwork_philiplackner.di

import android.content.Context
import com.google.gson.Gson
import com.prmto.socialnetwork_philiplackner.feature_post.data.data_source.remote.PostApi
import com.prmto.socialnetwork_philiplackner.feature_post.data.repository.PostRepositoryImpl
import com.prmto.socialnetwork_philiplackner.feature_post.domain.repository.PostRepository
import com.prmto.socialnetwork_philiplackner.feature_post.domain.use_case.CreatePostUseCase
import com.prmto.socialnetwork_philiplackner.feature_post.domain.use_case.GetPostsFollowsUseCase
import com.prmto.socialnetwork_philiplackner.feature_post.domain.use_case.PostUseCases
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
object PostModule {

    @Provides
    @Singleton
    fun providePostApi(client: OkHttpClient): PostApi {
        return Retrofit.Builder()
            .baseUrl(PostApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(PostApi::class.java)
    }

    @Provides
    @Singleton
    fun providePostRepository(
        postApi: PostApi,
        gson: Gson,
        @ApplicationContext appContext: Context
    ): PostRepository {
        return PostRepositoryImpl(postApi, gson, appContext)
    }

    @Provides
    @Singleton
    fun providePostUseCase(repository: PostRepository): PostUseCases {
        return PostUseCases(
            getPostsFollowsUseCase = GetPostsFollowsUseCase(repository),
            createPostUseCase = CreatePostUseCase(repository)
        )
    }
}