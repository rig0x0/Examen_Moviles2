package com.example.remc_exa_ll.services

import com.example.remc_exa_ll.data.ApiPost
import retrofit2.http.GET
import retrofit2.http.Path

interface PostService {
    @GET("posts")
    suspend fun getAllPosts(): List<ApiPost>

    @GET("users/{id}/posts")
    suspend fun getPostsByUserId(@Path("id") id: Int): List<ApiPost>
}