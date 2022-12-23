package com.prmto.socialnetwork_philiplackner.feature_profile.domain.model

data class UpdateProfileData(
    val username: String,
    val bio: String,
    val gitHubUrl: String,
    val instagramUrl: String,
    val linkedInUrl: String,
    val skills: List<Skill>,
    val profileImageChanged: Boolean = false
)
