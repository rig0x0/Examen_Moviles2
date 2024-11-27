package com.example.remc_exa_ll.utils

import com.example.remc_exa_ll.entity.UserEntity

data class UsersResponse(
    val estado: Int,
    val msg: String,
    val users: List<UserEntity>,
)

