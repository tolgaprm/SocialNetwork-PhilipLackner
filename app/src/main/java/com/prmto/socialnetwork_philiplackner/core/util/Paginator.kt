package com.prmto.socialnetwork_philiplackner.core.util

interface Paginator<T> {

    suspend fun loadNextItems()
}