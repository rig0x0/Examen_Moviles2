package com.example.remc_exa_ll.services

import com.example.remc_exa_ll.data.ApiUser
import retrofit2.http.GET

interface UserService {
    @GET("users")
    suspend fun getAllUsers(): List<ApiUser>
}