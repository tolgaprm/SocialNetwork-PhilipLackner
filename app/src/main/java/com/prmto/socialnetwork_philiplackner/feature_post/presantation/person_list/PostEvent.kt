package com.prmto.socialnetwork_philiplackner.feature_post.presantation.person_list

import com.prmto.socialnetwork_philiplackner.core.util.Event

sealed class PostEvent :Event(){
    object LikedPost : PostEvent()
}
