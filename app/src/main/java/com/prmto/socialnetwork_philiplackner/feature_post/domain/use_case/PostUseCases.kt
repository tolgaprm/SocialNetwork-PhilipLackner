package com.prmto.socialnetwork_philiplackner.feature_post.domain.use_case

data class PostUseCases(
    val getPostsFollowsUseCase: GetPostsFollowsUseCase,
    val createPostUseCase: CreatePostUseCase,
    val getPostDetails: GetPostDetailsUseCase,
    val getCommentsForPost: GetCommentsForPostUseCase,
    val createComment:CreateCommentUseCase
)
