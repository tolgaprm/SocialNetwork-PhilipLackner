package com.prmto.socialnetwork_philiplackner.core.domain.repository

import android.net.Uri
import com.prmto.socialnetwork_philiplackner.core.domain.models.Post
import com.prmto.socialnetwork_philiplackner.core.domain.models.UserItem
import com.prmto.socialnetwork_philiplackner.core.util.Constants
import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.core.util.SimpleResource
import com.prmto.socialnetwork_philiplackner.feature_profile.domain.model.Profile
import com.prmto.socialnetwork_philiplackner.feature_profile.domain.model.Skill
import com.prmto.socialnetwork_philiplackner.feature_profile.domain.model.UpdateProfileData

interface ProfileRepository {
    suspend fun getPostsPaged(
        page: Int = 0,
        pageSize: Int = Constants.DEFAULT_PAGE_SIZE,
        userId: String
    ): Resource<List<Post>>

    suspend fun getProfile(userId: String): Resource<Profile>

    suspend fun updateProfile(
        updateProfileData: UpdateProfileData,
        bannerImageUri: Uri?,
        profilePictureUri: Uri?
    ): SimpleResource

    suspend fun getSkills(): Resource<List<Skill>>

    suspend fun searchUSer(query: String): Resource<List<UserItem>>

    suspend fun followUser(userId: String): SimpleResource

    suspend fun unFollowUser(userId: String): SimpleResource

}