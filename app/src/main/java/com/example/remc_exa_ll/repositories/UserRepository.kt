package com.example.remc_exa_ll.repositories

import com.example.remc_exa_ll.data.ApiUser
import com.example.remc_exa_ll.network.ClienteRetrofit
import com.example.remc_exa_ll.services.UserService

class UserRepository(private val UserService: UserService = ClienteRetrofit.getInstanciaRetrofit) {
    suspend fun getAllUsers(): List<ApiUser> {
        return UserService.getAllUsers()
    }
}