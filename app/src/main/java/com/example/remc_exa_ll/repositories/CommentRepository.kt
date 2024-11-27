package com.example.remc_exa_ll.repositories

import com.example.remc_exa_ll.data.ApiComment
import com.example.remc_exa_ll.network.ClienteRetrofit
import com.example.remc_exa_ll.services.CommentService

class CommentRepository(private val CommentService: CommentService = ClienteRetrofit.getInstanceCommentRetrofit) {
    suspend fun getAllComments(): List<ApiComment> {
        return CommentService.getAllComments()
    }

    suspend fun getCommentsByPostId(id: Int): List<ApiComment> {
        return CommentService.getCommentsByPostId(id)
    }
}