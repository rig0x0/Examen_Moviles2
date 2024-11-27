package com.example.remc_exa_ll.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.remc_exa_ll.entity.CommentEntity

@Dao
interface CommentDAO {
    @Insert
    suspend fun insertComment(comment: CommentEntity)

    @Query("SELECT * FROM comment WHERE postId = :postId")
    suspend fun getCommentsForPost(postId: Int): List<CommentEntity>

    @Query("SELECT * FROM comment WHERE id = :id")
    suspend fun getCommentById(id: Int): CommentEntity?
}