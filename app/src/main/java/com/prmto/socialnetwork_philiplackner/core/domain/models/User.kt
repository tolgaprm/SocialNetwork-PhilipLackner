package com.prmto.socialnetwork_philiplackner.core.domain.models

data class User(
    val userId:String,
    val profilePictureUrl: String,
    val userName: String,
    val bio: String,
    val followerCount: Int,
    val followingCount: Int,
    val postCount:Int
)
