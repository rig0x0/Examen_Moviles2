package com.example.remc_exa_ll.repositories

import com.example.remc_exa_ll.data.ApiPost
import com.example.remc_exa_ll.network.ClienteRetrofit
import com.example.remc_exa_ll.services.PostService

class PostRepository(private val PostService: PostService = ClienteRetrofit.getInstancePostRetrofit) {
    suspend fun getAllPosts(): List<ApiPost> {
        return PostService.getAllPosts()
    }

    suspend fun getPostsByUserId(id: Int): List<ApiPost> {
        return PostService.getPostsByUserId(id)
    }
}