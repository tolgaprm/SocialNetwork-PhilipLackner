package com.prmto.socialnetwork_philiplackner.feature_activity.data.remote

import com.prmto.socialnetwork_philiplackner.core.util.Constants.DEFAULT_PAGE_SIZE
import com.prmto.socialnetwork_philiplackner.feature_activity.data.remote.dto.ActivityDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ActivityApi {

    @GET("/api/activity/get")
    suspend fun getActivities(
        @Query("page") page: Int = 0,
        @Query("pageSize") pageSize: Int = DEFAULT_PAGE_SIZE
    ): List<ActivityDto>


    companion object {
        val BASE_URL = "http://10.0.2.2:8001"
    }
}