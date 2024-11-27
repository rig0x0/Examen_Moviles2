package com.example.remc_exa_ll.utils

import com.example.remc_exa_ll.entity.PostEntity

data class PostsReponse (
    val estado: Int,
    val msg: String,
    val posts: List<PostEntity>,
)