package com.prmto.socialnetwork_philiplackner.core.domain.models

data class Post(
    val id:String,
    val username: String,
    val imageUrl: String,
    val profilePictureProfile: String,
    val description: String,
    val likeCount: Int,
    val commentCount: Int
)
