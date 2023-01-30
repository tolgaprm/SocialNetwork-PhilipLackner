package com.prmto.socialnetwork_philiplackner.di

import com.google.gson.Gson
import com.prmto.socialnetwork_philiplackner.core.data.remote.PostApi
import com.prmto.socialnetwork_philiplackner.feature_post.data.repository.PostRepositoryImpl
import com.prmto.socialnetwork_philiplackner.feature_post.domain.repository.PostRepository
import com.prmto.socialnetwork_philiplackner.feature_post.domain.use_case.*
import dagger.Provides
import dagger.hilt.InstallIn
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
        gson: Gson
    ): PostRepository {
        return PostRepositoryImpl(postApi, gson)
    }

    @Provides
    @Singleton
    fun providePostUseCase(repository: PostRepository): PostUseCases {
        return PostUseCases(
            getPostsFollowsUseCase = GetPostsFollowsUseCase(repository),
            createPostUseCase = CreatePostUseCase(repository),
            getPostDetails = GetPostDetailsUseCase(repository),
            getCommentsForPost = GetCommentsForPostUseCase(repository)
        )
    }
}