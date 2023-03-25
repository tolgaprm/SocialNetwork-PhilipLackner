package com.prmto.socialnetwork_philiplackner.feature_chat.di

import android.app.Application
import com.google.gson.Gson
import com.prmto.socialnetwork_philiplackner.feature_chat.data.remote.ws.util.CustomGsonMessageAdapter
import com.prmto.socialnetwork_philiplackner.feature_chat.data.remote.ws.util.FlowStreamAdapter
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatModule {


    @OptIn(ExperimentalCoroutinesApi::class)
    @Provides
    @Singleton
    fun provideScarlet(app:Application,okHttpClient: OkHttpClient, gson: Gson): Scarlet {
        return Scarlet.Builder()
            .webSocketFactory(okHttpClient.newWebSocketFactory("ws://192.168.0.2:8001/api/chat/websocket"))
            .addMessageAdapterFactory(CustomGsonMessageAdapter.Factory(gson))
            .addStreamAdapterFactory(FlowStreamAdapter.Factory)
            .lifecycle(AndroidLifecycle.ofApplicationForeground(app))
            .build()
    }
}