package com.example.remc_exa_ll.utils

import com.example.remc_exa_ll.entity.CommentEntity

data class CommentsReponse(
    val estado: Int,
    val msg: String,
    val comments: List<CommentEntity>,
)
