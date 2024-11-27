package com.example.remc_exa_ll.services

import com.example.remc_exa_ll.data.ApiComment
import retrofit2.http.GET
import retrofit2.http.Path

interface CommentService {
    @GET("comments")
    suspend fun getAllComments(): List<ApiComment>

    @GET("posts/{id}/comments")
    suspend fun getCommentsByPostId(@Path("id") id: Int): List<ApiComment>
}