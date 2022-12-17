package com.prmto.socialnetwork_philiplackner.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.Gson
import com.prmto.socialnetwork_philiplackner.core.util.Constants
import com.prmto.socialnetwork_philiplackner.core.util.Constants.SHARED_PREFERENCES_NAME
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
object Module {


    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences(
            SHARED_PREFERENCES_NAME,
            MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun provideJwtToken(
        sharedPreferences: SharedPreferences
    ): String {
        return sharedPreferences.getString(Constants.KEY_JWT_TOKEN, "") ?: ""
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        token: String
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor {
                val modifiedRequest = it.request().newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
                it.proceed(modifiedRequest)
            }.build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }
}